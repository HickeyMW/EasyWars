package com.wickeddevs.easywars.ui.noclan.verifyjoin;

import com.wickeddevs.easywars.data.model.JoinDecision;
import com.wickeddevs.easywars.data.model.User;
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.data.service.contract.ApiService;
import com.wickeddevs.easywars.data.service.contract.JoinClanService;
import com.wickeddevs.easywars.data.service.contract.UserService;

import javax.inject.Inject;

/**
 * Created by hicke_000 on 8/3/2016.
 */
public class VerifyJoinClanPresenter implements VerifyJoinClanContract.ViewListener {

    private VerifyJoinClanContract.View view;
    private ApiService apiService;
    private JoinClanService joinClanService;
    private UserService userService;

    private boolean loadedClanInfo = false;

    @Inject
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
    public void onCreate() {
        view.toggleLoading(true);
        joinClanService.setDecisionListener(new JoinClanService.DecisionListener() {
            @Override
            public void onUpdate(final JoinDecision joinDecision) {
                if (joinDecision.isApproved == JoinDecision.APPROVED) {
                    view.navigateToHomeUi();
                } else {
                    if (!loadedClanInfo) {
                        loadDisplayClanInfo();
                        loadedClanInfo = true;
                    }
                    if (joinDecision.isApproved == JoinDecision.DENIED) {
                        view.displayJoinDenied();
                    }
                }
            }
        });
    }

    @Override
    public void cancelJoinClan() {
        joinClanService.removeJoinRequest(true);
        view.navigateToNoClanUi();
    }

    private void loadDisplayClanInfo() {
        userService.getUser(new UserService.LoadUserCallback() {
            @Override
            public void onUserLoaded(User user) {
                apiService.getApiClan(user.clanTag, new ApiService.LoadApiClanCallback() {
                    @Override
                    public void onApiClanLoaded(ApiClan apiClan) {
                        view.toggleLoading(false);
                        view.displayJoinInfo(apiClan);
                    }
                });
            }
        });
    }
}
