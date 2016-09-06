package com.wickeddevs.easywars.ui.loadingsplash;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;

/**
 * Created by 375csptssce on 7/26/16.
 */
public interface LoadingSplashContract {

    interface View extends PView {

        void displayBehindMajorVersion();

        void displayBehindMinorVersion();

        void closeApp();

        void navigateToLoginUi();

        void navigateToHomeUi(boolean isAdmin);

        void navigateToNoClanUi();

        void navigateToCreatingClanUi();

        void navigateToJoiningClanUi();
    }

    interface ViewListener extends Presenter<LoadingSplashContract.View> {

        void onCreate();

        void returnedFromLogin(boolean successful);

        void pressedOkMajor();

        void pressedOkMinor();
    }
}
