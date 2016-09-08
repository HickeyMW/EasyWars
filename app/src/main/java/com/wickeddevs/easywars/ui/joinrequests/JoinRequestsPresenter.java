package com.wickeddevs.easywars.ui.joinrequests;

import com.wickeddevs.easywars.data.model.JoinRequest;
import com.wickeddevs.easywars.data.service.contract.JoinClanService;

import javax.inject.Inject;

/**
 * Created by 375csptssce on 8/15/16.
 */
public class JoinRequestsPresenter implements JoinRequestsContract.ViewListener {

    private final static String TAG = "JoinRequestsPresenter";

    private JoinRequestsContract.View view;
    private JoinClanService joinClanService;

    private boolean anyJoinRequests = false;

    @Inject
    public JoinRequestsPresenter(JoinClanService joinClanService) {
        this.joinClanService = joinClanService;
    }

    @Override
    public void registerView(JoinRequestsContract.View activity) {
        view = activity;
    }

    @Override
    public void onCreate() {
        view.toggleLoading(true);
        joinClanService.setJoinRequestListener(new JoinClanService.JoinRequestListener() {
            @Override
            public void addJoinRequest(JoinRequest joinRequest) {
                anyJoinRequests = true;
                view.addJoinRequest(joinRequest);
            }

            @Override
            public void initialLoadComplete() {
                if (!anyJoinRequests) {
                    view.displayMessage("There are no join requests to display");
                }
                view.toggleLoading(false);
            }
        });
    }

    @Override
    public void joinRequestDecision(JoinRequest joinRequest, boolean approved) {
        joinClanService.setDecision(joinRequest, approved);
    }
}
