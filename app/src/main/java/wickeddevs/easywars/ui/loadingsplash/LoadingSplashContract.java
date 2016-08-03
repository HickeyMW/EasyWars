package wickeddevs.easywars.ui.loadingsplash;

import wickeddevs.easywars.base.PView;
import wickeddevs.easywars.base.Presenter;

/**
 * Created by 375csptssce on 7/26/16.
 */
public interface LoadingSplashContract {

    interface View extends PView {

        void showLoginUi();

        void showHomeUi(boolean isAdmin);

        void showNoClanUi();

        void showLoginError();
    }

    interface ViewListener extends Presenter<LoadingSplashContract.View> {

        void returnedFromLogin(boolean successful);
    }
}
