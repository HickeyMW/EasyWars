package com.wickeddevs.easywars.data.service.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import com.wickeddevs.easywars.data.model.JoinDecision;
import com.wickeddevs.easywars.data.model.JoinRequest;
import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.model.User;
import com.wickeddevs.easywars.data.service.contract.JoinClanService;

/**
 * Created by hicke_000 on 8/2/2016.
 */
public class FbJoinClanService implements JoinClanService {

    @Override
    public void setDecisionListener(final DecisionListener listener) {
        FbInfo.getJoinDecisionRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(final DatabaseReference dbRef) {
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    JoinDecision joinDecision = dataSnapshot.getValue(JoinDecision.class);
                    if (listener == null) {
                        dbRef.removeEventListener(this);
                    } else if (joinDecision == null) {
                        listener.onUpdate(new JoinDecision());
                    } else {
                        listener.onUpdate(joinDecision);
                        if (joinDecision.isApproved != JoinDecision.PENDING) {
                            dbRef.removeEventListener(this);
                            if (joinDecision.isApproved == JoinDecision.APPROVED) {
                                FbInfo.setState(User.STATE_MEMBER);
                            } else {
                                FbInfo.setState(User.STATE_BLANK);
                            }
                            removeJoinRequest();
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
    public void removeJoinRequest() {
        FbInfo.getJoinRequestRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.removeValue();
            }
        });
        FbInfo.getJoinDecisionRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.removeValue();
            }
        });
        FbInfo.setState(User.STATE_BLANK);
    }

    @Override
    public void setJoinRequest(String clanTag, final JoinRequest joinRequest) {
        FbInfo.getJoinRequestRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.setValue(joinRequest);
            }
        });
        User user = new User(User.STATE_JOINING, clanTag);
        FbInfo.setUser(user);
        FbInfo.setState(User.STATE_JOINING);
        FbInfo.setClanTag(clanTag);
    }

    @Override
    public void setJoinRequestListener(final JoinRequestListener listener) {
        FbInfo.getJoinRequestsRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(final DatabaseReference dbRef) {
                dbRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (listener == null) {
                            dbRef.removeEventListener(this);
                        } else {
                            JoinRequest joinRequest = dataSnapshot.getValue(JoinRequest.class);
                            joinRequest.key = dataSnapshot.getKey();
                            listener.addJoinRequest(joinRequest);
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        listener.initialLoadComplete();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public void setDecision(final JoinRequest joinRequest, final boolean approved) {
        FbInfo.getJoinRequestsRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.child(joinRequest.key).removeValue();
                FbInfo.getJoinDecisionsRef(new FbInfo.DbRefCallback() {
                    @Override
                    public void onLoaded(DatabaseReference dbRef) {
                        dbRef.child(joinRequest.key).setValue(new JoinDecision(approved));
                        if (approved) {
                            FbInfo.getClanMembersRef(new FbInfo.DbRefCallback() {
                                @Override
                                public void onLoaded(DatabaseReference dbRef) {
                                    dbRef.child(joinRequest.key).setValue(new Member(joinRequest.name, false, joinRequest.key));
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    @Override
    public void searchJoinableClans(final String query, final ClanTagsCallback callback) {
        FbInfo.getAllClansRef().orderByChild("name").equalTo(query).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> clanTags = new ArrayList<String>();
                Iterator<DataSnapshot> snapshots = dataSnapshot.getChildren().iterator();
                while (snapshots.hasNext()) {
                    DataSnapshot ds = snapshots.next();
                    String clanTag = "#"  + ds.getKey();
                    clanTags.add(clanTag);
                }
                callback.onLoaded(clanTags);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
