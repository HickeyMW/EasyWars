package wickeddevs.easywars.ui.noclan.verifyjoin;

import wickeddevs.easywars.data.model.JoinDecision;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.JoinClanService;
import wickeddevs.easywars.data.service.contract.StateService;

/**
 * Created by hicke_000 on 8/3/2016.
 */
public class VerifyJoinClanPresenter implements VerifyJoinClanContract.ViewListener {

    private VerifyJoinClanContract.View joiningClanView;
    private ApiService apiService;
    private JoinClanService joinClanService;
    private StateService stateService;

    public VerifyJoinClanPresenter(ApiService apiService, JoinClanService joinClanService, StateService stateService) {
        this.apiService = apiService;
        this.joinClanService = joinClanService;
        this.stateService = stateService;
    }

    @Override
    public void registerView(VerifyJoinClanContract.View activity) {
        joiningClanView = activity;
    }

    @Override
    public void onAttach() {
        joinClanService.setDecisionListener(new JoinClanService.DecisionListener() {
            @Override
            public void onUpdate(final JoinDecision joinDecision) {
                if (joinDecision.state == JoinDecision.APPROVED) {
                    joiningClanView.navigateToHomeUi();
                } else {
                    apiService.getApiClan(stateService.getClanTag(), new ApiService.LoadApiClanCallback() {
                        @Override
                        public void onApiClanLoaded(ApiClan apiClan) {
                            joiningClanView.displayJoinInfo(apiClan);
                            if (joinDecision.state == JoinDecision.DENIED) {
                                joiningClanView.displayJoinDenied();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void cancelJoinClan() {
        joinClanService.cancelJoinRequest();
    }
}
