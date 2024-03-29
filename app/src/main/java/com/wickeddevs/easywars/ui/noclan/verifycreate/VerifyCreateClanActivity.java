package com.wickeddevs.easywars.ui.noclan.verifycreate;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import com.wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.easywars.data.model.CreateRequest;
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.databinding.ActivityVerifyCreateClanBinding;
import com.wickeddevs.easywars.miscellaneous.HideProgressBarRequestListener;
import com.wickeddevs.easywars.ui.home.HomeActivity;
import com.wickeddevs.easywars.ui.noclan.NoClanActivity;

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
        presenter.onCreate();
    }

    @Override
    public void displayCreateRequestDetails(CreateRequest createRequest, ApiClan apiClan) {
        binding.tvClanName.setText(apiClan.name);
        binding.tvTag.setText(apiClan.tag);
        binding.tvVerification.setText("Code: " + String.valueOf(createRequest.verification));
        Glide.with(this).load(apiClan.badgeUrls.medium).centerCrop()
                .listener(new HideProgressBarRequestListener(binding.badgeProgressBar)).into(binding.ivBadge);
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.cancelCreateClan();
            }
        });
    }

    @Override
    public void navigateToHomeUi() {
        startActivity(HomeActivity.getInstance(this, true));
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
            DaggerViewInjectorComponent.builder()
                    .serviceComponent(DaggerServiceComponent.create())
                    .build().inject(this);
        }
        return presenter;
    }

    @Override
    public void toggleLoading(boolean loading) {
        if (loading) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.layoutMain.setVisibility(View.INVISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.layoutMain.setVisibility(View.VISIBLE);
        }
    }
}
