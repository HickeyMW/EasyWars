package wickeddevs.easywars.loadingsplash;

import wickeddevs.easywars.Presenter;

/**
 * Created by 375csptssce on 7/26/16.
 */
public interface LoadingSplashContract {

    interface View {

        void showLoginUi();

        void showHomeUi(boolean isAdmin);

        void showCreatingClanUi();

        void showJoiningClanUi();

        void showNoClanUi();
    }

    interface ViewListener extends Presenter {

        void returnedFromLogin(boolean successful);
    }
}
