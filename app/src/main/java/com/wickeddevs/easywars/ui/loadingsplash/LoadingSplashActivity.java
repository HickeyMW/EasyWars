package com.wickeddevs.easywars.ui.loadingsplash;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;

import javax.inject.Inject;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.Injector;
import com.wickeddevs.easywars.databinding.ActivityLoadingSplashBinding;
import com.wickeddevs.easywars.ui.home.HomeActivity;
import com.wickeddevs.easywars.ui.noclan.NoClanActivity;
import com.wickeddevs.easywars.ui.noclan.verifycreate.VerifyCreateClanActivity;
import com.wickeddevs.easywars.ui.noclan.verifyjoin.VerifyJoinClanActivity;

public class LoadingSplashActivity extends BasePresenterActivity<LoadingSplashContract.ViewListener> implements LoadingSplashContract.View {

    final static String TAG = "LoadingSplashActivity";

    final int RC_SIGN_IN = 0;

    @Inject
    public LoadingSplashContract.ViewListener presenter;

    private ActivityLoadingSplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loading_splash);
        presenter.onCreate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                presenter.returnedFromLogin(true);
            } else {
                presenter.returnedFromLogin(false);
            }
        }
    }

    @Override
    public void displayBehindMajorVersion() {
        new AlertDialog.Builder(this)
                .setTitle("Old Version")
                .setMessage("This version is incompatible with the changes in the new version. Please download the new version to continues using this app")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.pressedOkMajor();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void displayBehindMinorVersion() {
        new AlertDialog.Builder(this)
                .setTitle("Old Version")
                .setMessage("This version is compatible with the new changes but to get the latest features and fixes download the new version")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.pressedOkMinor();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void closeApp() {
        finish();
    }

    @Override
    public void navigateToLoginUi() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), RC_SIGN_IN);
    }

    @Override
    public void navigateToHomeUi(boolean isAdmin) {
        startActivity(HomeActivity.getInstance(this, isAdmin));
        finish();
    }

    @Override
    public void navigateToNoClanUi() {
        Intent i = new Intent(this, NoClanActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void navigateToCreatingClanUi() {
        Intent i = new Intent(this, VerifyCreateClanActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void navigateToJoiningClanUi() {
        Intent i = new Intent(this, VerifyJoinClanActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected LoadingSplashContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
        }
        return presenter;
    }
}
