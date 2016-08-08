package wickeddevs.easywars.ui.loadingsplash;

import android.os.Handler;
import android.util.Log;

import wickeddevs.easywars.data.service.contract.StateService;

/**
 * Created by 375csptssce on 7/26/16.
 */
public class LoadingSplashPresenter implements LoadingSplashContract.ViewListener {

    private final static String TAG = "LoadingSplashPresenter";

    private LoadingSplashContract.View loadingSplashView;

    private StateService stateService;

    public LoadingSplashPresenter(StateService stateService) {
        this.stateService = stateService;
    }


    @Override
    public void registerView(LoadingSplashContract.View activity) {
        loadingSplashView = activity;
    }

    @Override
    public void onAttach() {
        if (stateService.isLoggedIn()) {
            navigateOnUserState(0);
        } else {
            loadingSplashView.navigateToLoginUi();
        }
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void returnedFromLogin(boolean successful) {
        if (successful) {
            navigateOnUserState(0);
        } else {
            loadingSplashView.displayError("Error logging in. Please try again.");
        }
    }

    private void navigateOnUserState(final int tries) {

        if (tries > 100) {
            loadingSplashView.displayError("Couldn't load account info. Please try again");
        } else {
            switch (stateService.getState()) {
                case StateService.STATE_LOADING:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "run: " + String.valueOf(tries));
                            navigateOnUserState(1 + tries);
                        }
                    },100);
                    break;
                case StateService.STATE_BLANK:
                    loadingSplashView.navigateToNoClanUi();
                    break;
                case StateService.STATE_CREATING:
                    loadingSplashView.navigateToCreatingClanUi();
                    break;
                case StateService.STATE_JOINING:
                    loadingSplashView.navigateToJoiningClanUi();
                    break;
                case StateService.STATE_MEMBER:
                case StateService.STATE_ADMIN:
                    loadingSplashView.navigateToHomeUi();
                    break;
                default:
                    Log.e(TAG, "User doesn't have a valid state");
            }
        }

    }
}
