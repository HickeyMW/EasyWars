package com.wickeddevs.easywars.data.service.firebase;

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
                        if (joinDecision != null) {
                            listener.onUpdate(joinDecision);
                            if (joinDecision.approved != JoinDecision.PENDING) {
                                dbRef.removeEventListener(this);
                                if (joinDecision.approved == JoinDecision.APPROVED) {
                                    removeJoinRequest();
                                    FbInfo.setState(User.STATE_MEMBER);
                                } else {
                                    FbInfo.setState(User.STATE_BLANK);
                                }
                            }
                        } else {
                            listener.onUpdate(new JoinDecision());
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
    public void getJoinRequests(final JoinRequestsCallback callback) {
        FbInfo.getJoinRequestsRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> snapshots = dataSnapshot.getChildren().iterator();
                        ArrayList<JoinRequest> joinRequests = new ArrayList<>();
                        while (snapshots.hasNext()) {
                            DataSnapshot ds = snapshots.next();
                            JoinRequest joinRequest = ds.getValue(JoinRequest.class);
                            joinRequest.uid = ds.getKey();
                            joinRequests.add(joinRequest);
                        }
                        callback.onLoaded(joinRequests);
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
                dbRef.child(joinRequest.uid).removeValue();
                FbInfo.getJoinDecisionsRef(new FbInfo.DbRefCallback() {
                    @Override
                    public void onLoaded(DatabaseReference dbRef) {
                        dbRef.child(joinRequest.uid).setValue(new JoinDecision(approved));
                        if (approved) {
                            FbInfo.getClanMembersRef(new FbInfo.DbRefCallback() {
                                @Override
                                public void onLoaded(DatabaseReference dbRef) {
                                    dbRef.child(joinRequest.uid).setValue(new Member(joinRequest.name, false));
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
