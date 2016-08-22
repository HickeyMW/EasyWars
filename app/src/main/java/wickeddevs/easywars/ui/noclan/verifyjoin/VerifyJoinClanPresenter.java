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

    private VerifyJoinClanContract.View view;
    private ApiService apiService;
    private JoinClanService joinClanService;
    private UserService userService;

    private boolean loadedClanInfo = false;

    public VerifyJoinClanPresenter(ApiService apiService, JoinClanService joinClanService, UserService userService) {
        this.apiService = apiService;
        this.joinClanService = joinClanService;
        this.userService = userService;
    }

    @Override
    public void registerView(VerifyJoinClanContract.View activity) {
        view = activity;
    }

    @Override
    public void onAttach() {
        joinClanService.setDecisionListener(new JoinClanService.DecisionListener() {
            @Override
            public void onUpdate(final JoinDecision joinDecision) {
            if (joinDecision.approved == JoinDecision.APPROVED) {
                view.navigateToHomeUi();
            } else {
                if (!loadedClanInfo) {
                    loadDisplayClanInfo();
                    loadedClanInfo = true;
                }
                if (joinDecision.approved == JoinDecision.DENIED) {
                    view.displayJoinDenied();
                }
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
        view.navigateToNoClanUi();
    }

    private void loadDisplayClanInfo() {
        view.toggleProgressBar(true);
        userService.getUser(new UserService.LoadUserCallback() {
            @Override
            public void onUserLoaded(User user) {
                apiService.getApiClan(user.clanTag, new ApiService.LoadApiClanCallback() {
                    @Override
                    public void onApiClanLoaded(ApiClan apiClan) {
                        view.toggleProgressBar(false);
                        view.displayJoinInfo(apiClan);
                    }
                });
            }
        });
    }

//    private void loadDisplayClanInfo(final int joinDecision) {
//        view.toggleProgressBar(true);
//        userService.getUser(new UserService.LoadUserCallback() {
//            @Override
//            public void onUserLoaded(User user) {
//                apiService.getApiClan(user.clanTag, new ApiService.LoadApiClanCallback() {
//                    @Override
//                    public void onApiClanLoaded(ApiClan apiClan) {
//                        view.toggleProgressBar(false);
//                        view.displayJoinInfo(apiClan);
//                        if (joinDecision == JoinDecision.DENIED) {
//                            view.displayJoinDenied();
//                        }
//                    }
//                });
//            }
//        });
//    }
}
