package com.wickeddevs.easywars.ui.joinrequests;

import com.wickeddevs.easywars.data.model.JoinRequest;
import com.wickeddevs.easywars.data.service.contract.JoinClanService;

/**
 * Created by 375csptssce on 8/15/16.
 */
public class JoinRequestsPresenter implements JoinRequestsContract.ViewListener {

    private final static String TAG = "JoinRequestsPresenter";

    private JoinRequestsContract.View view;
    private JoinClanService joinClanService;

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
                view.addJoinRequest(joinRequest);
            }

            @Override
            public void intialLoadComplete() {
                view.toggleLoading(false);
            }
        });
//        joinClanService.getJoinRequests(new JoinClanService.JoinRequestsCallback() {
//            @Override
//            public void onLoaded(ArrayList<JoinRequest> joinRequests) {
//                if (joinRequests.size() != 0) {
//                    view.displayJoinRequests(joinRequests);
//                } else {
//                    view.displayNoJoinRequests();
//                }
//            }
//        });
    }

    @Override
    public void joinRequestDecision(JoinRequest joinRequest, boolean approved) {
        joinClanService.setDecision(joinRequest, approved);
    }
}
