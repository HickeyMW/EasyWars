package wickeddevs.easywars.data.service.contract;

import java.util.ArrayList;

import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.model.JoinDecision;
import wickeddevs.easywars.data.model.JoinRequest;

/**
 * Created by 375csptssce on 7/28/16.
 */
public interface JoinClanService {

    interface DecisionListener {

        void onUpdate(JoinDecision joinDecision);
    }

    interface JoinRequestsCallback {

        void onLoaded(ArrayList<JoinRequest> joinRequests);
    }

    void setDecisionListener(DecisionListener listener);

    void removeJoinRequest();

    void setJoinRequest(String clanTag, JoinRequest joinRequest);

    void getJoinRequests(JoinRequestsCallback callback);

    void setDecision(JoinRequest joinRequest, boolean approved);
}
