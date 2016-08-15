package wickeddevs.easywars.ui.noclan.verifycreate;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import wickeddevs.easywars.R;
import wickeddevs.easywars.base.BasePresenterActivity;
import wickeddevs.easywars.dagger.Injector;
import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.databinding.ActivityVerifyCreateClanBinding;
import wickeddevs.easywars.ui.home.HomeActivity;
import wickeddevs.easywars.ui.noclan.NoClanActivity;

public class VerifyCreateClanActivity extends BasePresenterActivity<VerifyCreateClanContract.ViewListener> implements VerifyCreateClanContract.View {

    @Inject
    public VerifyCreateClanContract.ViewListener presenter;
    private ActivityVerifyCreateClanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_create_clan);
        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.verifyCreateClan();
            }
        });
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.cancelCreateClan();
            }
        });
    }

    @Override
    public void displayCreateRequestDetails(CreateRequest createRequest, ApiClan apiClan) {
        binding.layoutMain.setVisibility(View.VISIBLE);
        binding.tvClanName.setText(apiClan.name);
        binding.tvTag.setText(apiClan.tag);
        binding.tvVerification.setText("Code: " + String.valueOf(createRequest.verification));
        Glide.with(this).load(apiClan.badgeUrls.medium).centerCrop().into(binding.ivBadge);
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.cancelCreateClan();
            }
        });
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
        if (loading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void displayMessage(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();


    }
}
