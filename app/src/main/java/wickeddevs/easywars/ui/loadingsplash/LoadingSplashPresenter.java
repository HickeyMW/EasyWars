package wickeddevs.easywars.ui.loadingsplash;

import android.os.Handler;
import android.util.Log;

import wickeddevs.easywars.data.model.User;
import wickeddevs.easywars.data.service.contract.UserService;

/**
 * Created by 375csptssce on 7/26/16.
 */
public class LoadingSplashPresenter implements LoadingSplashContract.ViewListener {

    private final static String TAG = "LoadingSplashPresenter";

    private LoadingSplashContract.View loadingSplashView;
    private UserService mUserService;

    public LoadingSplashPresenter(UserService userService) {
        this.mUserService = userService;
    }


    @Override
    public void registerView(LoadingSplashContract.View activity) {
        loadingSplashView = activity;
    }

    @Override
    public void onAttach() {
        if (mUserService.isLoggedIn()) {
            navigateOnUserState();
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
            navigateOnUserState();
        } else {
            loadingSplashView.displayMessage("Error logging in. Please try again.");
        }
    }

    private void navigateOnUserState() {

        mUserService.getUser(new UserService.LoadUserCallback() {
            @Override
            public void onUserLoaded(User user) {
                switch (user.state) {
                    case User.STATE_BLANK:
                        loadingSplashView.navigateToNoClanUi();
                        break;
                    case User.STATE_CREATING:
                        loadingSplashView.navigateToCreatingClanUi();
                        break;
                    case User.STATE_JOINING:
                        loadingSplashView.navigateToJoiningClanUi();
                        break;
                    case User.STATE_MEMBER:
                    case User.STATE_ADMIN:
                        loadingSplashView.navigateToHomeUi();
                        break;
                    default:
                        Log.e(TAG, "User doesn't have a valid state");
                }
            }
        });
    }
}
