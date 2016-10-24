package com.wickeddevs.easywars.data.service.firebase;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.wickeddevs.easywars.data.model.war.Attack;
import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.data.model.war.Comment;
import com.wickeddevs.easywars.data.model.war.Participent;
import com.wickeddevs.easywars.data.model.war.War;
import com.wickeddevs.easywars.data.model.war.WarInfo;
import com.wickeddevs.easywars.data.service.contract.WarService;
import com.wickeddevs.easywars.util.Shared;

/**
 * Created by 375csptssce on 8/18/16.
 */
public class FbWarService implements WarService, ChildEventListener {

    final static String TAG = "FbWarService";

    private GenericTypeIndicator<ArrayList<Participent>> gtiArrayListParticipents = new GenericTypeIndicator<ArrayList<Participent>>() {};
    private GenericTypeIndicator<ArrayList<Base>> gtiArrayListBases = new GenericTypeIndicator<ArrayList<Base>>() {};

    LoadBaseListener listener;
    DatabaseReference commentRef;
    DatabaseReference attackRef;
    int baseId;

    @Override
    public void setLatestWarListener(final LoadWarCallback listener) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                final Query query = dbRef.limitToLast(1);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        if (iterator.hasNext()) {
                            DataSnapshot ds = iterator.next();
                            if (ds.hasChild("participants")) {
                                War war = ds.getValue(War.class);
                                war.key = ds.getKey();
                                ArrayList<Participent> participents = ds.child("participants").getValue(gtiArrayListParticipents);
                                String myUid = FbInfo.getUid();
                                for (Participent participent : participents) {
                                    if (participent.uid != null) {
                                        if (participent.uid.equals(myUid)) {
                                            war.isParticipent = true;
                                            break;
                                        }
                                    }
                                }

                                for (int i = 0; i < war.bases.size(); i++) {
                                    war.bases.get(i).key = String.valueOf(i);
                                }
                                Iterator<DataSnapshot> iter = ds.child("attacks").getChildren().iterator();
                                while(iter.hasNext()) {
                                    DataSnapshot dsAttack = iter.next();
                                    Attack attack = dsAttack.getValue(Attack.class);
                                    Base base = war.bases.get(attack.base);
                                    if (attack.stars > -1) {
                                        base.attacks.add(attack);
                                        if (attack.stars > base.stars) {
                                            base.stars = attack.stars;
                                        }
                                    } else {
                                        base.claims.add(attack);
                                    }
                                }
                                if (listener != null) {
                                    listener.onLoaded(war);
                                } else {
                                    query.removeEventListener(this);
                                }
                            }


                        } else {
                            if (listener != null) {
                                listener.onLoaded(null);
                            } else {
                                query.removeEventListener(this);
                            }
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
                            ArrayList<Participent> participents = ds.child("participants").getValue(gtiArrayListParticipents);
                            String myUid = FbInfo.getUid();
                            for (Participent participent : participents) {
                                if (participent.uid != null) {
                                    if (participent.uid.equals(myUid)) {
                                        war.isParticipent = true;
                                        break;
                                    }
                                }
                            }

                            for (int i = 0; i < war.bases.size(); i++) {
                                war.bases.get(i).key = String.valueOf(i);
                            }
                            Iterator<DataSnapshot> iter = ds.child("attacks").getChildren().iterator();
                            while(iter.hasNext()) {
                                DataSnapshot dsAttack = iter.next();
                                Attack attack = dsAttack.getValue(Attack.class);
                                Base base = war.bases.get(attack.base);
                                if (attack.stars > -1) {
                                    base.attacks.add(attack);
                                    if (attack.stars > base.stars) {
                                        base.stars = attack.stars;
                                    }
                                } else {
                                    base.claims.add(attack);
                                }
                            }

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
    public void getLatestWarOverview(final LoadOverviewCallback callback) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        if (iterator.hasNext()) {
                            DataSnapshot dsWar = iterator.next();
                            ArrayList<Participent> participents = dsWar.child("participants").getValue(gtiArrayListParticipents);
                            Iterator<DataSnapshot> iter = dsWar.child("attacks").getChildren().iterator();
                            while(iter.hasNext()) {
                                DataSnapshot dsAttack = iter.next();
                                Attack attack = dsAttack.getValue(Attack.class);
                                attack.baseName = dsWar.child("bases/" + attack.base + "/name").getValue(String.class);
                                for (Participent participent : participents) {
                                    if (participent.uid != null) {
                                        if (attack.uid.equals(participent.uid)) {
                                            participent.attackClaims.add(attack);
                                            break;
                                        }
                                    } else {
                                        if (attack.uid.equals(participent.name)) {
                                            participent.attackClaims.add(attack);
                                            break;
                                        }
                                    }

                                }
                            }
                            callback.onLoaded(participents);
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
    public void setLatestWarOverviewListener(final LoadOverviewCallback listener) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                final Query query = dbRef.limitToLast(1);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        if (iterator.hasNext()) {
                            DataSnapshot dsWar = iterator.next();
                            if (dsWar.hasChild("participants")) {
                                ArrayList<Participent> participents = dsWar.child("participants").getValue(gtiArrayListParticipents);
                                if (dsWar.hasChild("attacks")) {
                                    Iterator<DataSnapshot> iter = dsWar.child("attacks").getChildren().iterator();
                                    while(iter.hasNext()) {
                                        DataSnapshot dsAttack = iter.next();
                                        Attack attack = dsAttack.getValue(Attack.class);
                                        attack.baseName = dsWar.child("bases/" + attack.base + "/name").getValue(String.class);
                                        for (Participent participent : participents) {
                                            if (participent.uid != null) {
                                                if (attack.uid.equals(participent.uid)) {
                                                    participent.attackClaims.add(attack);
                                                    break;
                                                }
                                            } else {
                                                if (attack.uid.equals(participent.name)) {
                                                    participent.attackClaims.add(attack);
                                                    break;
                                                }
                                            }

                                        }
                                    }
                                }

                                if (listener != null) {
                                    listener.onLoaded(participents);
                                } else {
                                    query.removeEventListener(this);
                                }
                            }

                        } else {
                            if (listener != null) {
                                listener.onLoaded(null);
                            } else {
                                query.removeEventListener(this);
                            }
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
        FbInfo.getUserRef().child("creatingWar/warInfo").setValue(warInfo);
    }

    @Override
    public void saveBaseInfo(final ArrayList<Base> bases) {
        FbInfo.getUserRef().child("creatingWar/bases").setValue(bases);
    }

    @Override
    public void startWar(final ArrayList<Participent> participents) {
        FbInfo.getUserRef().child("creatingWar").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final WarInfo warInfo = dataSnapshot.child("warInfo").getValue(WarInfo.class);
                final ArrayList<Base> bases = dataSnapshot.child("bases").getValue(gtiArrayListBases);
                FbInfo.getWarRef(new FbInfo.DbRefCallback() {
                    @Override
                    public void onLoaded(DatabaseReference dbRef) {
                        DatabaseReference warRef = dbRef.push();
                        warRef.child("warInfo").setValue(warInfo);
                        warRef.child("bases").setValue(bases);
                        warRef.child("participants").setValue(participents);
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
                dbRef.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        if (iterator.hasNext()) {
                            DataSnapshot ds = iterator.next();
                            ds.child("participants").getRef().removeValue();
                            ds.child("warInfo").getRef().removeValue();
                            ds.child("bases").getRef().removeValue();
                            Iterator<DataSnapshot> iter = ds.child("comments").getChildren().iterator();
                            while (iter.hasNext()) {
                                iter.next().getRef().removeValue();
                            }
                            Iterator<DataSnapshot> it = ds.child("attacks").getChildren().iterator();
                            while (it.hasNext()) {
                                it.next().getRef().removeValue();
                            }
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
    public void setBaseListener(final String warId, final int baseId, final LoadBaseListener listener) {
        this.listener = listener;
        this.baseId = baseId;
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(final DatabaseReference dbRef) {
                dbRef.child(warId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataSnapshot dsBase = dataSnapshot.child("bases/" + baseId);
                        Base base = dsBase.getValue(Base.class);
                        base.key = dsBase.getKey();
                        listener.onLoaded(base);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                commentRef = dbRef.child(warId + "/comments");
                attackRef = dbRef.child(warId + "/attacks");
                commentRef.addChildEventListener(FbWarService.this);
                attackRef.addChildEventListener(FbWarService.this);

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
    public void saveAttack(final Attack attack) {
        if (attack.uid == null) {
            attack.uid = FbInfo.getUid();
        }
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        if (iterator.hasNext()) {
                            DataSnapshot dsWar = iterator.next();
                            if (attack.key != null) {
                                dsWar.child("attacks/" + attack.key).getRef().setValue(attack);
                            } else {
                                dsWar.child("attacks").getRef().push().setValue(attack);
                            }

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
    public void deleteAttack(final Attack attack) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        if (iterator.hasNext()) {
                            DataSnapshot dsWar = iterator.next();
                            dsWar.child("attacks/" + attack.key).getRef().removeValue();
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
    public void getLatestWarAttacks(final LoadAttacksCallback callback) {
        getLatestWarAttacks(FbInfo.getUid(), callback);
    }

    @Override
    public void getLatestWarAttacks(final String participentKey, final LoadAttacksCallback callback) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        if (iterator.hasNext()) {
                            DataSnapshot dsWar = iterator.next();
                            DataSnapshot dsAttacks = dsWar.child("attacks");
                            ArrayList<Base> bases = dsWar.child("bases").getValue(gtiArrayListBases);

                            ArrayList<Attack> attacks = new ArrayList<Attack>();
                            Iterator<DataSnapshot> iterDsAttacks = dsAttacks.getChildren().iterator();
                            while (iterDsAttacks.hasNext()) {
                                DataSnapshot dsAttack = iterDsAttacks.next();
                                Attack attack = dsAttack.getValue(Attack.class);
                                attack.key = dsAttack.getKey();
                                if (attack.uid.equals(participentKey)) {
                                    attacks.add(attack);
                                    Base base = bases.get(attack.base);
                                    attack.thLevel = base.thLevel;
                                    attack.baseName = base.name;
                                    if (attacks.size() > 2) {
                                        Log.e(TAG, "onDataChange: More than two attacks for user");
                                    }
                                }
                            }
                            callback.onLoaded(attacks);
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
            Attack attack = dataSnapshot.getValue(Attack.class);
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
