package com.wickeddevs.easywars.ui.noclan.verifycreate;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.Injector;
import com.wickeddevs.easywars.data.model.CreateRequest;
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.databinding.ActivityVerifyCreateClanBinding;
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
        binding.layoutMain.setVisibility(View.VISIBLE);
        binding.tvClanName.setText(apiClan.name);
        binding.tvTag.setText(apiClan.tag);
        binding.tvVerification.setText("Code: " + String.valueOf(createRequest.verification));
        Glide.with(this).load(apiClan.badgeUrls.medium).centerCrop().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                binding.badgeProgressBar.setVisibility(View.INVISIBLE);
                return false;
            }
        }).into(binding.ivBadge);
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
    public void toggleLoading(boolean loading) {
        if (loading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
