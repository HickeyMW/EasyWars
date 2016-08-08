package wickeddevs.easywars.data.service.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import wickeddevs.easywars.data.model.JoinDecision;
import wickeddevs.easywars.data.model.JoinRequest;
import wickeddevs.easywars.data.model.User;
import wickeddevs.easywars.data.service.contract.JoinClanService;
import wickeddevs.easywars.data.service.contract.StateService;

/**
 * Created by hicke_000 on 8/2/2016.
 */
public class FbJoinClanService implements JoinClanService {

    private DatabaseReference getJoinRequestRef() {
        return FbInfo.INSTANCE.getDb().getReference("joinRequests/" + FbInfo.INSTANCE.getNoHashClanTag() + "/requests/" + FbInfo.INSTANCE.getUid());
    }

    private DatabaseReference getJoinDecisionRef() {
        return FbInfo.INSTANCE.getDb().getReference("joinRequests/" + FbInfo.INSTANCE.getNoHashClanTag() + "/decisions/" + FbInfo.INSTANCE.getUid());
    }

    @Override
    public void setDecisionListener(final DecisionListener listener) {
        getJoinDecisionRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                JoinDecision joinDecision = dataSnapshot.getValue(JoinDecision.class);
                if (joinDecision != null) {
                    listener.onUpdate(joinDecision);
                    if (joinDecision.approved != 0) {
                        getJoinDecisionRef().removeEventListener(this);
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

    @Override
    public void removeJoinRequest() {
        getJoinRequestRef().removeValue();
        getJoinDecisionRef().removeValue();
        FbInfo.INSTANCE.setState(User.BLANK);
    }

    @Override
    public void setJoinRequest(String clanTag, JoinRequest joinRequest) {
        getJoinRequestRef().setValue(joinRequest);
        FbInfo.INSTANCE.setClanTag(clanTag);
        FbInfo.INSTANCE.setState(User.JOINING);
    }
}
