package com.wickeddevs.easywars.ui.noclan.verifycreate;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.CreateRequest;
import com.wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by hicke_000 on 8/3/2016.
 */
public interface VerifyCreateClanContract {

    interface View extends PView {

        void displayCreateRequestDetails(CreateRequest createRequest, ApiClan apiClan);

        void navigateToHomeUi();

        void navigateToNoClanUi();

        void toggleLoading(boolean loading);
    }

    interface ViewListener extends Presenter<VerifyCreateClanContract.View> {

        void verifyCreateClan();

        void cancelCreateClan();

    }
}
