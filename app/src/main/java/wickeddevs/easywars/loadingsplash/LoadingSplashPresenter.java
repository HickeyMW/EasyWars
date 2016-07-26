package wickeddevs.easywars.loadingsplash;

import wickeddevs.easywars.Presenter;
import wickeddevs.easywars.data.Services;

/**
 * Created by 375csptssce on 7/26/16.
 */
public class LoadingSplashPresenter implements LoadingSplashContract.ViewListener {

    private final Services.User mUserService;

    private final LoadingSplashContract.View mLoadingSplashView;

    public LoadingSplashPresenter(Services.User userService, LoadingSplashContract.View loadingSplashView) {
        mUserService = userService;
        mLoadingSplashView = loadingSplashView;
    }

    @Override
    public void start() {

        mUserService.isLoggedIn(new Services.User.LoggedInCallback() {
            @Override
            public void response(boolean isLoggedIn) {
                if (isLoggedIn) {
                    mLoadingSplashView.showHomeUi();
                } else {
                    mLoadingSplashView.showLoginUi();
                }
            }
        });
    }

    @Override
    public void stop() {

    }
}
