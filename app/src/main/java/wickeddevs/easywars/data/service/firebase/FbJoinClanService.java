package wickeddevs.easywars.data.service.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import wickeddevs.easywars.data.model.JoinDecision;
import wickeddevs.easywars.data.model.JoinRequest;
import wickeddevs.easywars.data.model.User;
import wickeddevs.easywars.data.service.contract.JoinClanService;
import wickeddevs.easywars.data.service.contract.UserService;

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
                            if (joinDecision.approved != 0) {
                                dbRef.removeEventListener(this);
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
}
