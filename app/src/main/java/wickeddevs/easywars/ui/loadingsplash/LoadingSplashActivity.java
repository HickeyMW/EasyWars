package wickeddevs.easywars.ui.loadingsplash;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;

import javax.inject.Inject;

import wickeddevs.easywars.R;
import wickeddevs.easywars.base.BasePresenterActivity;
import wickeddevs.easywars.dagger.Injector;
import wickeddevs.easywars.databinding.ActivityLoadingSplashBinding;
import wickeddevs.easywars.ui.home.HomeActivity;
import wickeddevs.easywars.ui.noclan.NoClanActivity;
import wickeddevs.easywars.ui.noclan.verifycreate.VerifyCreateClanActivity;
import wickeddevs.easywars.ui.noclan.verifyjoin.VerifyJoinClanActivity;

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
    public void displayError(String error) {

    }

    @Override
    public void navigateToLoginUi() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), RC_SIGN_IN);
    }

    @Override
    public void navigateToHomeUi() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
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

    @Override
    public void toggleProgressBar(boolean loading) {

    }

    @Override
    public void displayToast(String toast) {

    }
}
