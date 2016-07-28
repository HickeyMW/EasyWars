package wickeddevs.easywars.data.service.contract;

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

    void setDecisionListener(DecisionListener listener);

    void deleteJoinRequest();

    void setJoinRequest(JoinRequest joinRequest);
}
