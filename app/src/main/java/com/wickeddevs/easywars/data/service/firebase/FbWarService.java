package com.wickeddevs.easywars.data.service.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.data.model.war.Comment;
import com.wickeddevs.easywars.data.model.war.War;
import com.wickeddevs.easywars.data.service.contract.WarService;

/**
 * Created by 375csptssce on 8/18/16.
 */
public class FbWarService implements WarService, ChildEventListener {

    LoadBaseListener listener;
    DatabaseReference commentRef;
    DatabaseReference claimRef;

    int claimsToIgnore = 0;
    int commentsToIgnore = 0;

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
    public void startWar(final War war) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                DatabaseReference warRef = dbRef.push();
                warRef.child("startTime").setValue(war.startTime);
                warRef.child("enemyName").setValue(war.enemyName);
                warRef.child("enemyTag").setValue(war.enemyTag);
                warRef.child("bases").setValue(war.bases);
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
    public void setBaseListener(final String warId, final String baseId, final LoadBaseListener listener) {
        this.listener = listener;
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(final DatabaseReference dbRef) {
                dbRef.child(warId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Base base = dataSnapshot.child("bases/" + baseId).getValue(Base.class);
                        DataSnapshot ds = dataSnapshot.child("claims/" + baseId);
                        if (ds.hasChild(FbInfo.getUid())) {
                            base.didClaim = true;
                        }
                        Iterator<DataSnapshot> iterator = ds.getChildren().iterator();
                        ArrayList<String> claims = new ArrayList<>();
                        while (iterator.hasNext()) {
                            claims.add(iterator.next().getKey());
                        }

                        Iterator<DataSnapshot> iter = dataSnapshot.child("comments/" + baseId).getChildren().iterator();
                        ArrayList<Comment> comments = new ArrayList<>();
                        while (iter.hasNext()) {
                            comments.add(iter.next().getValue(Comment.class));
                        }
                        claimsToIgnore = claims.size();
                        commentsToIgnore = comments.size();
                        base.claims = claims;
                        base.comments = comments;
                        listener.onLoaded(base);
                        commentRef = dbRef.child(warId + "/comments/" + baseId);
                        claimRef = dbRef.child(warId + "/claims/" + baseId);
                        commentRef.addChildEventListener(FbWarService.this);
                        claimRef.addChildEventListener(FbWarService.this);
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
        claimRef.removeEventListener(this);
    }

    @Override
    public void claimBase(final String warId, final String baseId) {
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("timestamp", ServerValue.TIMESTAMP);
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.child(warId + "/claims/" + baseId + "/" + FbInfo.getUid()).setValue(hashMap);

            }
        });
    }

    @Override
    public void removeClaim(final String warId, final String baseId) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.child(warId + "/claims/" + baseId + "/" + FbInfo.getUid()).removeValue();

            }
        });
    }

    @Override
    public void addComment(final String body, final String warId, final String baseId) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                Comment comment = new Comment(FbInfo.getUid(), body, System.currentTimeMillis());
                dbRef.child(warId + "/comments/" + baseId).push().setValue(comment);
            }
        });
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot.hasChild("body")) {
            if (commentsToIgnore > 0) {
                commentsToIgnore--;

            } else {
                Comment comment = dataSnapshot.getValue(Comment.class);
                listener.newComment(comment);
            }
        } else {
            if (claimsToIgnore > 0) {
                claimsToIgnore--;
            } else {
                String claim = dataSnapshot.getKey();
                listener.newClaim(claim);
            }
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        if (dataSnapshot.hasChild("body")) {

        } else {
            String claim = dataSnapshot.getKey();
            listener.removeClaim(claim);
        }
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
