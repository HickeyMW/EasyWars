package wickeddevs.easywars.ui.noclan.verifyjoin;

import wickeddevs.easywars.data.model.JoinDecision;
import wickeddevs.easywars.data.model.User;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.JoinClanService;
import wickeddevs.easywars.data.service.contract.UserService;

/**
 * Created by hicke_000 on 8/3/2016.
 */
public class VerifyJoinClanPresenter implements VerifyJoinClanContract.ViewListener {

    private VerifyJoinClanContract.View joiningClanView;
    private ApiService apiService;
    private JoinClanService joinClanService;
    private UserService mUserService;

    public VerifyJoinClanPresenter(ApiService apiService, JoinClanService joinClanService, UserService userService) {
        this.apiService = apiService;
        this.joinClanService = joinClanService;
        this.mUserService = userService;
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
            if (joinDecision.approved == JoinDecision.APPROVED) {
                joiningClanView.navigateToHomeUi();
            } else {
                loadDisplayClanInfo(joinDecision.approved);
            }
            }
        });

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void cancelJoinClan() {
        joinClanService.removeJoinRequest();
        joiningClanView.navigateToNoClanUi();
    }

    private void loadDisplayClanInfo(final int joinDecision) {
        mUserService.getUser(new UserService.LoadUserCallback() {
            @Override
            public void onUserLoaded(User user) {
                apiService.getApiClan(user.clanTag, new ApiService.LoadApiClanCallback() {
                    @Override
                    public void onApiClanLoaded(ApiClan apiClan) {
                        joiningClanView.displayJoinInfo(apiClan);
                        if (joinDecision == JoinDecision.DENIED) {
                            joiningClanView.displayJoinDenied();
                        }
                    }
                });
            }
        });
    }
}
