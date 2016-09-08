package com.wickeddevs.easywars.data.service.firebase;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.wickeddevs.easywars.data.model.war.Attack;
import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.data.model.war.Comment;
import com.wickeddevs.easywars.data.model.war.War;
import com.wickeddevs.easywars.data.model.war.WarInfo;
import com.wickeddevs.easywars.data.service.contract.WarService;
import com.wickeddevs.easywars.util.Shared;

/**
 * Created by 375csptssce on 8/18/16.
 */
public class FbWarService implements WarService, ChildEventListener {

    final static String TAG = "FbWarService";

    LoadBaseListener listener;
    DatabaseReference commentRef;
    DatabaseReference attackRef;
    int baseId;

    @Override
    public void getLatestWar(final LoadWarCallback callback) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        if (iterator.hasNext()) {
                            DataSnapshot ds = iterator.next();
                            War war = ds.getValue(War.class);
                            war.key = ds.getKey();
//                            Iterator<DataSnapshot> iter = ds.child("claims").getChildren().iterator();
//                            while(iter.hasNext()) {
//                                DataSnapshot claimSnap = iter.next();
//                                Base base = war.bases.get(Integer.valueOf(claimSnap.getKey()));
//                                base.claims.add()
//                            }

                            callback.onLoaded(war);
                        } else {
                            callback.onLoaded(null);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public void saveWarInfo(WarInfo warInfo) {
        FbInfo.getUserRef().child("creatingWar").setValue(warInfo);
    }

    @Override
    public void startWar(final ArrayList<Base> bases) {
        FbInfo.getUserRef().child("creatingWar").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final WarInfo warInfo = dataSnapshot.getValue(WarInfo.class);
                FbInfo.getWarRef(new FbInfo.DbRefCallback() {
                    @Override
                    public void onLoaded(DatabaseReference dbRef) {
                        DatabaseReference warRef = dbRef.push();
                        warRef.child("warInfo").setValue(warInfo);
                        warRef.child("bases").setValue(bases);
                        dataSnapshot.getRef().removeValue();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void deleteWar() {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.removeValue();
            }
        });
    }

    @Override
    public void setBaseListener(final String warId, final int baseId, final LoadBaseListener listener) {
        this.listener = listener;
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(final DatabaseReference dbRef) {
                commentRef = dbRef.child(warId + "/comments");
                attackRef = dbRef.child(warId + "/attacks");
                commentRef.addChildEventListener(FbWarService.this);
                attackRef.addChildEventListener(FbWarService.this);
                dbRef.child(warId + "/bases/" + baseId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Base base = dataSnapshot.getValue(Base.class);
                        base.key = dataSnapshot.getKey();
                        listener.onLoaded(base);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public void removeBaseListener() {
        listener = null;
        commentRef.removeEventListener(this);
        attackRef.removeEventListener(this);
    }

    @Override
    public void claimBase(final String warId, final int baseId) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                HashMap<String, Object> hashMap = Attack.createAttackClaimHashMap(baseId);
                dbRef.child(warId + "/attacks").push().setValue(hashMap);
            }
        });
    }

    @Override
    public void removeClaim(final String warId, final Attack attack) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.child(warId + "/attacks/" + attack.key).removeValue();
            }
        });
    }

    @Override
    public void getAttacks(final String warId, final LoadAttacksCallback callback) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.child(warId + "/attacks").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<Attack> attacks = new ArrayList<Attack>();
                        Iterator<DataSnapshot> iterDsAttacks = dataSnapshot.getChildren().iterator();
                        while (iterDsAttacks.hasNext()) {
                            DataSnapshot dsAttack = iterDsAttacks.next();
                            Attack attack = dsAttack.getValue(Attack.class);
                            attack.key = dsAttack.getKey();
                            if (attack.uid == FbInfo.getUid()) {
                                attacks.add(attack);
                                if (attacks.size() > 2) {
                                    Log.e(TAG, "onDataChange: More than two attacks for user");
                                }
                            }
                        }
                        callback.onLoaded(attacks);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public void addComment(final String body, final String warId, final int baseId) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                HashMap<String, Object> hashMap = Comment.createCommentHashMap(FbInfo.getUid(), body, baseId);
                dbRef.child(warId + "/comments").push().setValue(hashMap);
            }
        });
    }

    @Override
    public void isActiveWar(final ActiveWarCallback callback) {
        getLatestWar(new LoadWarCallback() {
            @Override
            public void onLoaded(War war) {
                if (war != null && (war.warInfo.startTime > (System.currentTimeMillis() - Shared.MILIS_IN_TWO_DAYS))) {
                    callback.onLoaded(true);
                } else {
                    callback.onLoaded(false);
                }
            }
        });
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot.hasChild("body")) {
            Comment comment = dataSnapshot.getValue(Comment.class);
            comment.key = dataSnapshot.getKey();
            if (comment.base == baseId) {
                listener.newComment(comment);
            }
        } else if (dataSnapshot.hasChild("stars")) {
            Attack attack = new Attack(dataSnapshot.getKey(), baseId);
            attack.key = dataSnapshot.getKey();
            if (attack.base == baseId && attack.stars == -1) {
                listener.newClaim(attack);
            }
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        if (dataSnapshot.hasChild("body")) {

        } else if (dataSnapshot.hasChild("stars")) {
            Attack attack = new Attack(dataSnapshot.getKey(), baseId);
            attack.key = dataSnapshot.getKey();
            if (attack.base == baseId && attack.stars == -1) {
                listener.removeClaim(attack);
            }
        }
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
