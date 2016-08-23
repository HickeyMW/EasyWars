package com.wickeddevs.easywars.ui.joinrequests;

import java.util.ArrayList;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.JoinRequest;

/**
 * Created by 375csptssce on 8/15/16.
 */
public interface JoinRequestsContract {

    interface View extends PView {

        void displayJoinRequests(ArrayList<JoinRequest> joinRequests);

        void displayNoJoinRequests();
    }

    interface ViewListener extends Presenter<JoinRequestsContract.View> {

        void joinRequestDecision(JoinRequest joinRequest, boolean approved);
    }
}
