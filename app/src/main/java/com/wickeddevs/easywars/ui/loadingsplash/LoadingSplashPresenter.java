package com.wickeddevs.easywars.ui.loadingsplash;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.wickeddevs.easywars.data.model.User;
import com.wickeddevs.easywars.data.service.contract.UserService;
import com.wickeddevs.easywars.data.service.contract.VersionService;

/**
 * Created by 375csptssce on 7/26/16.
 */
public class LoadingSplashPresenter implements LoadingSplashContract.ViewListener {

    private final static String TAG = "LoadingSplashPresenter";
    private final int majorVersion = 0;
    private final int minorVersion = 0;

    private LoadingSplashContract.View view;
    private UserService userService;
    private VersionService versionService;

    public LoadingSplashPresenter(UserService userService, VersionService versionService) {
        this.userService = userService;
        this.versionService = versionService;
    }

    @Override
    public void registerView(LoadingSplashContract.View activity) {
        view = activity;
    }

    @Override
    public void onAttach() {
        versionService.getCurrentVersion(new VersionService.CheckVersionCallback() {
            @Override
            public void onVersionLoaded(int major, int minor) {
                if (major > majorVersion) {
                    view.displayBehindMajorVersion();
                } else if (minor > minorVersion) {
                    view.displayBehindMinorVersion();
                } else {
                    startLogin();
                }
            }
        });
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

    @Override
    public void pressedOkMajor() {
        view.closeApp();
    }

    @Override
    public void pressedOkMinor() {
        startLogin();
    }

    private void startLogin() {
        if (userService.isLoggedIn()) {
            navigateOnUserState();
        } else {
            view.navigateToLoginUi();
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
