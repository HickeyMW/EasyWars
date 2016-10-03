package com.wickeddevs.easywars.ui.noclan.create;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public interface CreateClanContract {

    interface View extends PView {

        String getClanTag();

        void displayClanInfo(ApiClan apiClan);

        void allowCreate();

        void navigateToVerifyCreateClanUi();

        void toggleLoading(boolean loading);
    }

    interface ViewListener extends Presenter<CreateClanContract.View> {

        void onCreate();

        void selectedName(String name);

        void selectedThLevel(int thLevel);

        void createClanRequest();
    }
}
