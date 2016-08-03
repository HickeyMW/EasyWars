package wickeddevs.easywars.data.service.firebase;

import wickeddevs.easywars.data.model.JoinRequest;
import wickeddevs.easywars.data.service.contract.JoinClanService;
import wickeddevs.easywars.data.service.contract.StateService;

/**
 * Created by hicke_000 on 8/2/2016.
 */
public class FbJoinClanService implements JoinClanService {

    public StateService stateService;

    public FbJoinClanService(StateService stateService) {
        this.stateService = stateService;
    }

    @Override
    public void setDecisionListener(DecisionListener listener) {

    }

    @Override
    public void deleteJoinRequest() {

    }

    @Override
    public void setJoinRequest(JoinRequest joinRequest) {

    }
}
