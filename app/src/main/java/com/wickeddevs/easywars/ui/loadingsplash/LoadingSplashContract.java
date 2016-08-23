package com.wickeddevs.easywars.ui.loadingsplash;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;

/**
 * Created by 375csptssce on 7/26/16.
 */
public interface LoadingSplashContract {

    interface View extends PView {

        void navigateToLoginUi();

        void navigateToHomeUi();

        void navigateToNoClanUi();

        void navigateToCreatingClanUi();

        void navigateToJoiningClanUi();
    }

    interface ViewListener extends Presenter<LoadingSplashContract.View> {

        void returnedFromLogin(boolean successful);
    }
}
