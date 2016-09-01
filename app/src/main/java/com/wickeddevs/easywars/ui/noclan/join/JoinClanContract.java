package com.wickeddevs.easywars.ui.noclan.join;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by hicke_000 on 8/3/2016.
 */
public interface JoinClanContract {

    interface View extends PView {

        void allowJoin();

        String getClanTag();

        void displayClanInfo(ApiClan apiClan);

        void navigateToVerifyJoinClanUi();

        void toggleLoading(boolean loading);
    }

    interface ViewListener extends Presenter<JoinClanContract.View> {

        void onCreate();

        void selectedName(String name);

        void requestJoin(String message);
    }
}
