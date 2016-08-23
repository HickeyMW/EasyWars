package com.wickeddevs.easywars.ui.loadingsplash;

import android.util.Log;

import com.wickeddevs.easywars.data.model.User;
import com.wickeddevs.easywars.data.service.contract.UserService;

/**
 * Created by 375csptssce on 7/26/16.
 */
public class LoadingSplashPresenter implements LoadingSplashContract.ViewListener {

    private final static String TAG = "LoadingSplashPresenter";

    private LoadingSplashContract.View view;
    private UserService userService;

    public LoadingSplashPresenter(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void registerView(LoadingSplashContract.View activity) {
        view = activity;
    }

    @Override
    public void onAttach() {
        if (userService.isLoggedIn()) {
            navigateOnUserState();
        } else {
            view.navigateToLoginUi();
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
            view.displayMessage("Error logging in. Please try again.");
        }
    }

    private void navigateOnUserState() {

        userService.getUser(new UserService.LoadUserCallback() {
            @Override
            public void onUserLoaded(User user) {
                switch (user.state) {
                    case User.STATE_BLANK:
                        view.navigateToNoClanUi();
                        break;
                    case User.STATE_CREATING:
                        view.navigateToCreatingClanUi();
                        break;
                    case User.STATE_JOINING:
                        view.navigateToJoiningClanUi();
                        break;
                    case User.STATE_MEMBER:
                    case User.STATE_ADMIN:
                        view.navigateToHomeUi();
                        break;
                    default:
                        Log.e(TAG, "User doesn't have a valid state");
                }
            }
        });
    }
}
