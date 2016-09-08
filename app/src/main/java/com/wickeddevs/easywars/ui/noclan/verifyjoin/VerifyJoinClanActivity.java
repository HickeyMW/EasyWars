package com.wickeddevs.easywars.ui.noclan.verifyjoin;

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
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.databinding.ActivityVerifyJoinClanBinding;
import com.wickeddevs.easywars.miscellaneous.HideProgressBarRequestListener;
import com.wickeddevs.easywars.ui.home.HomeActivity;
import com.wickeddevs.easywars.ui.noclan.NoClanActivity;

public class VerifyJoinClanActivity extends BasePresenterActivity<VerifyJoinClanContract.ViewListener> implements VerifyJoinClanContract.View {

    @Inject
    public VerifyJoinClanContract.ViewListener presenter;
    private ActivityVerifyJoinClanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_join_clan);
        presenter.onCreate();
    }

    @Override
    public void displayJoinInfo(ApiClan apiClan) {
        binding.tvClanName.setText(apiClan.name);
        binding.tvTag.setText(apiClan.tag);
        Glide.with(this).load(apiClan.badgeUrls.medium).centerCrop()
                .listener(new HideProgressBarRequestListener(binding.progressBarBadge)).into(binding.ivBadge);
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.cancelJoinClan();
            }
        });
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
