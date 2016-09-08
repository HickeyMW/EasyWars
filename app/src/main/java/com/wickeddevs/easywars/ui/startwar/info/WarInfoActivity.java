package com.wickeddevs.easywars.ui.startwar.info;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import com.wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.easywars.databinding.ActivityWarInfoBinding;
import com.wickeddevs.easywars.miscellaneous.HoursTextWatcher;
import com.wickeddevs.easywars.miscellaneous.MinutesTextWatcher;
import com.wickeddevs.easywars.ui.shared.search.SearchClansActivity;
import com.wickeddevs.easywars.ui.startwar.warorder.WarOrderActivity;

import javax.inject.Inject;

public class WarInfoActivity extends BasePresenterActivity<WarInfoContract.ViewListener> implements
        WarInfoContract.View {

    private final static int RC_SEARCH = 0;
    private final static int RC_WAR_ORDER = 1;

    @Inject
    public WarInfoContract.ViewListener presenter;

    private ActivityWarInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_war_info);
        binding.tvHours.addTextChangedListener(new HoursTextWatcher());
        binding.tvMinutes.addTextChangedListener(new MinutesTextWatcher());
        binding.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.pressedIncreaseWarSize();
            }
        });
        binding.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.pressedDecreaseWarSize();
            }
        });
        binding.rbWarStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                presenter.switchedUntil(b);
            }
        });
        binding.layoutOpponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = SearchClansActivity.createIntent(WarInfoActivity.this, SearchClansActivity.STARTED_FOR_WAR);
                startActivityForResult(i, RC_SEARCH);
            }
        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.pressedNext();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RC_SEARCH) {
                String name = data.getStringExtra(SearchClansActivity.EXTRA_CLAN_NAME);
                String tag = data.getStringExtra(SearchClansActivity.EXTRA_CLAN_TAG);
                int members = data.getIntExtra(SearchClansActivity.EXTRA_CLAN_MEMBERS, -1);
                binding.tvOpponentName.setText(name);
                Glide.with(this).load(data.getStringExtra(SearchClansActivity.EXTRA_IMAGE_URL)).centerCrop().into(binding.ivBadge);
                binding.btnNext.setVisibility(View.VISIBLE);
                presenter.selectedEnemy(name, tag, members);
            } else if (requestCode == RC_WAR_ORDER) {
                finish();
            }
        }
    }

    @Override
    public void navigateToWarOrderUi(String clanTag, int warSize) {
        startActivityForResult(WarOrderActivity.createIntent(this, clanTag, warSize), RC_WAR_ORDER);
    }

    @Override
    protected WarInfoContract.ViewListener getPresenter() {
        if(presenter == null){
            DaggerViewInjectorComponent.builder()
                    .serviceComponent(DaggerServiceComponent.create())
                    .build().inject(this);
        }
        return presenter;
    }

    @Override
    public void setWarSizeText(String warSize) {
        binding.tvWarSize.setText(warSize);
    }
}
