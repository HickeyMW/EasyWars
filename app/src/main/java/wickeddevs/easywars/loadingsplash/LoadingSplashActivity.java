package wickeddevs.easywars.loadingsplash;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;

import wickeddevs.easywars.R;
import wickeddevs.easywars.databinding.ActivityLoadingSplashBinding;
import wickeddevs.easywars.home.HomeActivity;
import wickeddevs.easywars.util.Injection;

public class LoadingSplashActivity extends AppCompatActivity implements LoadingSplashContract.View {

    final static String TAG = "LoadingSplashActivity";

    final int RC_SIGN_IN = 0;

    private LoadingSplashContract.ViewListener presenter;
    private ActivityLoadingSplashBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_loading_splash);
        presenter = new LoadingSplashPresenter(this, Injection.userService());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                presenter.returnedFromLogin(true);
            } else {
                presenter.returnedFromLogin(true);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void showLoginUi() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), RC_SIGN_IN);
    }

    @Override
    public void showHomeUi(boolean isAdmin) {
        Intent i = HomeActivity.createIntent(this, isAdmin);
        startActivity(i);
        finish();
    }

    @Override
    public void showCreatingClanUi() {

    }

    @Override
    public void showJoiningClanUi() {

    }

    @Override
    public void showNoClanUi() {

    }

    @Override
    public void showLoginError() {

    }
}
