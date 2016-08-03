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
    public void toggleProgressBar(boolean loading) {

    }

    @Override
    public void displayToast(String toast) {

    }

    @Override
    protected LoadingSplashContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
        }
        return presenter;
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
    public void showNoClanUi() {
        Intent i = new Intent(this, NoClanActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void showLoginError() {

    }
}
