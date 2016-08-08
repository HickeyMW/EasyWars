package wickeddevs.easywars.ui.noclan.verifyjoin;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

import wickeddevs.easywars.R;
import wickeddevs.easywars.base.BasePresenterActivity;
import wickeddevs.easywars.dagger.Injector;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.databinding.ActivityVerifyJoinClanBinding;
import wickeddevs.easywars.ui.home.HomeActivity;
import wickeddevs.easywars.ui.noclan.NoClanActivity;

public class VerifyJoinClanActivity extends BasePresenterActivity<VerifyJoinClanContract.ViewListener> implements VerifyJoinClanContract.View {

    @Inject
    public VerifyJoinClanContract.ViewListener presenter;
    private ActivityVerifyJoinClanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_join_clan);
    }

    @Override
    public void displayJoinInfo(ApiClan apiClan) {
        binding.progressBar.setVisibility(View.GONE);
        binding.tvClanName.setText(apiClan.name);
        binding.tvTag.setText(apiClan.tag);
        binding.layoutJoining.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayJoinDenied() {
        binding.tvInstructions.setText("Your request to join the clan was denied");
        binding.btnCancel.setText("Back");
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
    protected VerifyJoinClanContract.ViewListener getPresenter() {
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
