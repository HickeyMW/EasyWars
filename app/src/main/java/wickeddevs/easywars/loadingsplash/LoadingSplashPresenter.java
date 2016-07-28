package wickeddevs.easywars.loadingsplash;

import android.util.Log;

import wickeddevs.easywars.data.Services;
import wickeddevs.easywars.data.model.User;

/**
 * Created by 375csptssce on 7/26/16.
 */
public class LoadingSplashPresenter implements LoadingSplashContract.ViewListener {

    final static String TAG = "LoadingSplashPresenter";

    private final LoadingSplashContract.View mLoadingSplashView;
    private final Services.UserService mUserService;

    public LoadingSplashPresenter(LoadingSplashContract.View loadingSplashView, Services.UserService userService) {
        mLoadingSplashView = loadingSplashView;
        mUserService = userService;
    }

    @Override
    public void returnedFromLogin(boolean successful) {
        if (successful) {
            navigateOnUserState();
        } else {
            mLoadingSplashView.showLoginError();
        }
    }

    @Override
    public void start() {
        if (mUserService.isLoggedIn()) {
            navigateOnUserState();
        } else {
            mLoadingSplashView.showLoginUi();
        }
    }

    private void navigateOnUserState() {
        mUserService.getUser(new Services.UserService.LoadUserCallback() {
            @Override
            public void onUserLoaded(User user) {
                switch (user.state) {
                    case User.BLANK:
                        mLoadingSplashView.showNoClanUi();
                        break;
                    case User.CREATING:
                        mLoadingSplashView.showCreatingClanUi();
                        break;
                    case User.JOINING:
                        mLoadingSplashView.showJoiningClanUi();
                        break;
                    case User.MEMBER:
                        mLoadingSplashView.showHomeUi(false);
                        break;
                    case User.ADMIN:
                        mLoadingSplashView.showHomeUi(true);
                        break;
                    default:
                        Log.e(TAG, "User doesn't have a state");
                }
            }
        });
    }
}
