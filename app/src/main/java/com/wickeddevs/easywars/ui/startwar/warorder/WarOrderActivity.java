package com.wickeddevs.easywars.ui.startwar.warorder;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.ClanMembersAdapter;
import com.wickeddevs.easywars.adapters.ThSelectorAdapter;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import com.wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.databinding.ActivityWarOrderBinding;
import com.wickeddevs.easywars.util.Shared;

import javax.inject.Inject;

public class WarOrderActivity extends BasePresenterActivity<WarOrderContract.ViewListener> implements
        WarOrderContract.View {

    public final static String EXTRA_CLAN_TAG = "EXTRA_CLAN_TAG";
    public final static String EXTRA_WAR_SIZE = "EXTRA_WAR_SIZE";

    private ClanMembersAdapter clanMembersAdapter;

    private AlertDialog dialogThSelector;

    @Inject
    public WarOrderContract.ViewListener presenter;

    private ActivityWarOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_war_order);
        binding.rvEnemyNames.setLayoutManager(new LinearLayoutManager(this));
        binding.btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.pressedUndo();
            }
        });
        binding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.pressedDone();
            }
        });
        presenter.onCreate();
    }

    @Override
    protected WarOrderContract.ViewListener getPresenter() {
        if(presenter == null){
            DaggerViewInjectorComponent.builder()
                    .serviceComponent(DaggerServiceComponent.create())
                    .build().inject(this);
        }
        return presenter;
    }

    @Override
    public void displayMember(String name, int thLevel) {
        binding.tvLastAdded.setText(name);
        binding.ivLastAdded.setImageResource(Shared.getThResource(thLevel));
    }

    @Override
    public void setRemainingText(String remainingText) {
        binding.tvRemaining.setText(remainingText);
    }

    @Override
    public void removeMember(int position) {
        clanMembersAdapter.remove(position);
    }

    @Override
    public void undoRemoveMember() {
        clanMembersAdapter.undo();
    }

    @Override
    public void allowDone(boolean allow) {
        if (allow) {
            binding.btnDone.setVisibility(View.VISIBLE);
        } else {
            binding.btnDone.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void dismiss() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void displayApiClan(ApiClan apiClan) {
        binding.tvEnemyName.setText(apiClan.name);
        binding.tvEnemyTag.setText(apiClan.tag);
        binding.tvRemaining.setText(String.valueOf(apiClan.members));
        Glide.with(this).load(apiClan.badgeUrls.medium).centerCrop().into(binding.ivEnemyBadge);
        clanMembersAdapter = new ClanMembersAdapter(apiClan.getMemberNames(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = binding.rvEnemyNames.getChildLayoutPosition(view);
                presenter.selectedName(clanMembersAdapter.getMember(position), position);
            }
        });
        binding.rvEnemyNames.setAdapter(clanMembersAdapter);
    }

    @Override
    public void displayThSelector() {

        View dialoglayout = getLayoutInflater().inflate(R.layout.dialog_th_selector, null);
        final RecyclerView rvThSelector = (RecyclerView) dialoglayout.findViewById(R.id.rvThSelector);
        rvThSelector.setLayoutManager(new GridLayoutManager(this, 3));
        rvThSelector.setAdapter(new ThSelectorAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = rvThSelector.getChildLayoutPosition(view);
                presenter.selectedTownHall(position + 1);
                dialogThSelector.dismiss();
            }
        }));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);
        dialogThSelector = builder.create();
        dialogThSelector.show();
    }

    @Override
    public String getClanTag() {
        return getIntent().getStringExtra(EXTRA_CLAN_TAG);
    }

    @Override
    public int getWarSize() {
        return getIntent().getIntExtra(EXTRA_WAR_SIZE, -1);
    }

    @Override
    public void toggleLoading(boolean loading) {
        if (loading) {
            binding.layoutMain.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.layoutMain.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public static Intent createIntent(Context context, String clanTag, int warSize) {
        Intent i = new Intent(context, WarOrderActivity.class);
        i.putExtra(EXTRA_CLAN_TAG, clanTag);
        i.putExtra(EXTRA_WAR_SIZE, warSize);
        return i;
    }
}
