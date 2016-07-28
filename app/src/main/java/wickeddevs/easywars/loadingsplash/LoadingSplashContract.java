package wickeddevs.easywars.loadingsplash;

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

        void showLoginError();
    }

    interface ViewListener {

        void start();

        void returnedFromLogin(boolean successful);
    }
}
