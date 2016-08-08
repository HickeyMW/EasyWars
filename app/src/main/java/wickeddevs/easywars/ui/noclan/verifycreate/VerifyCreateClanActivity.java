package wickeddevs.easywars.ui.noclan.verifycreate;

import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import wickeddevs.easywars.R;
import wickeddevs.easywars.base.BasePresenterActivity;
import wickeddevs.easywars.dagger.Injector;
import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.ui.home.HomeActivity;
import wickeddevs.easywars.ui.noclan.NoClanActivity;

public class VerifyCreateClanActivity extends BasePresenterActivity<VerifyCreateClanContract.ViewListener> implements VerifyCreateClanContract.View {

    @Inject
    public VerifyCreateClanContract.ViewListener presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_create_clan);
    }

    @Override
    public void displayCreateRequestDetails(CreateRequest createRequest, ApiClan apiClan) {

    }

    @Override
    public void displayCreateNotVerified() {

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
    protected VerifyCreateClanContract.ViewListener getPresenter() {
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
