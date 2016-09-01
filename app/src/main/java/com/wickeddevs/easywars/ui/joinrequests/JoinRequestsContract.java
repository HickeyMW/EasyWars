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

        void addJoinRequest(JoinRequest joinRequest);

        void displayNoJoinRequests();

        void toggleLoading(boolean loading);
    }

    interface ViewListener extends Presenter<JoinRequestsContract.View> {

        void onCreate();

        void joinRequestDecision(JoinRequest joinRequest, boolean approved);
    }
}
