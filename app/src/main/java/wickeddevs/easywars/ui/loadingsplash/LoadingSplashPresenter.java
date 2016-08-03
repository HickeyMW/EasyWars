package wickeddevs.easywars.ui.loadingsplash;

import android.os.Handler;
import android.util.Log;

import javax.inject.Inject;

import wickeddevs.easywars.data.model.User;
import wickeddevs.easywars.data.service.contract.StateService;

/**
 * Created by 375csptssce on 7/26/16.
 */
public class LoadingSplashPresenter implements LoadingSplashContract.ViewListener {

    final static String TAG = "LoadingSplashPresenter";

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
            navigateOnUserState();
        } else {
            loadingSplashView.showLoginUi();
        }
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void returnedFromLogin(boolean successful) {
        if (successful) {
            navigateOnUserState();
        } else {
            loadingSplashView.showLoginError();
        }
    }

    private void navigateOnUserState() {

        switch (stateService.getState()) {
            case StateService.STATE_LOADING:
                loadingSplashView.toggleProgressBar(true);
                Log.i(TAG, "navigateOnUserState: tick");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        navigateOnUserState();
                    }
                },100);
                //Make it wait
                break;
            case StateService.STATE_BLANK:
            case StateService.STATE_CREATING:
            case StateService.STATE_JOINING:
                loadingSplashView.showNoClanUi();
                break;
            case StateService.STATE_MEMBER:
                loadingSplashView.showHomeUi(false);
                break;
            case StateService.STATE_ADMIN:
                loadingSplashView.showHomeUi(true);
                break;
            default:
                Log.e(TAG, "User doesn't have a valid state");
        }
    }
}
