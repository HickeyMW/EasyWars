package wickeddevs.easywars.ui.joinrequests;

import java.util.ArrayList;

import wickeddevs.easywars.data.model.JoinRequest;
import wickeddevs.easywars.data.service.contract.JoinClanService;
import wickeddevs.easywars.ui.noclan.join.JoinClanContract;

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
    public void onAttach() {
        joinClanService.getJoinRequests(new JoinClanService.JoinRequestsCallback() {
            @Override
            public void onLoaded(ArrayList<JoinRequest> joinRequests) {
                if (joinRequests.size() != 0) {
                    view.displayJoinRequests(joinRequests);
                } else {
                    view.displayNoJoinRequests();
                }
            }
        });
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void joinRequestDecision(JoinRequest joinRequest, boolean approved) {
        joinClanService.setDecision(joinRequest, approved);
    }
}
