package com.wickeddevs.easywars.ui.noclan.verifyjoin;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by hicke_000 on 8/3/2016.
 */
public interface VerifyJoinClanContract {

    interface View extends PView {

        void displayJoinInfo(ApiClan apiClan);

        void displayJoinDenied();

        void navigateToHomeUi();

        void navigateToNoClanUi();

        void toggleLoading(boolean loading);
    }

    interface ViewListener extends Presenter<VerifyJoinClanContract.View> {

        void cancelJoinClan();
    }
}
