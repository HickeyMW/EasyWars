package com.wickeddevs.easywars.ui.noclan.create;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.recyclerview.ClanMembersRVA;
import com.wickeddevs.easywars.adapters.recyclerview.ThSelectorRVA;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import com.wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.databinding.ActivityCreateClanBinding;
import com.wickeddevs.easywars.ui.noclan.verifycreate.VerifyCreateClanActivity;
import com.wickeddevs.easywars.util.Shared;

public class CreateClanActivity extends BasePresenterActivity<CreateClanContract.ViewListener>
        implements CreateClanContract.View {

    public final static String EXTRA_CLAN_TAG = "EXTRA_CLAN_TAG";

    @Inject
    public CreateClanContract.ViewListener presenter;
    private ActivityCreateClanBinding binding;

    private AlertDialog dialogThSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_clan);
        binding.rvMembers.setLayoutManager(new LinearLayoutManager(this));
        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.createClanRequest();
            }
        });
        binding.layoutThLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialoglayout = getLayoutInflater().inflate(R.layout.dialog_th_selector, null);
                final RecyclerView rvThSelector = (RecyclerView) dialoglayout.findViewById(R.id.rvThSelector);
                rvThSelector.setLayoutManager(new GridLayoutManager(CreateClanActivity.this, 3));
                rvThSelector.setAdapter(new ThSelectorRVA(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int thLevel = rvThSelector.getChildLayoutPosition(view) + 1;
                        presenter.selectedThLevel(thLevel);
                        dialogThSelector.dismiss();
                        binding.ivTownHall.setImageResource(Shared.getThResource(thLevel));
                        binding.tvThLevel.setText("Town Hall Level " + thLevel);
                    }
                }));
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateClanActivity.this);
                builder.setView(dialoglayout);
                dialogThSelector = builder.create();
                dialogThSelector.show();
            }
        });
        presenter.onCreate();
    }

    @Override
    public void displayClanInfo(ApiClan apiClan) {
        binding.tvClanName.setText(apiClan.name);
        binding.tvMembers.setText("Members " + apiClan.members + "/50");
        Glide.with(this).load(apiClan.badgeUrls.medium).centerCrop().into(binding.ivClanBadge);
        binding.rvMembers.setAdapter(new ClanMembersRVA(apiClan.getMemberNames(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClanMembersRVA clanAdapter = (ClanMembersRVA) binding.rvMembers.getAdapter();
                String memberName = clanAdapter.getMember(binding.rvMembers.getChildLayoutPosition(view));
                presenter.selectedName(memberName);
                binding.tvSelectedName.setText(memberName);
            }
        }));
    }

    @Override
    public void allowCreate() {
        binding.btnCreate.setVisibility(View.VISIBLE);
    }

    @Override
    public String getClanTag() {
        return getIntent().getStringExtra(EXTRA_CLAN_TAG);
    }

    @Override
    public void navigateToVerifyCreateClanUi() {
        Intent i = new Intent(this, VerifyCreateClanActivity.class);
        startActivity(i);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected CreateClanContract.ViewListener getPresenter() {
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
            binding.layoutClan.setVisibility(View.INVISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.layoutClan.setVisibility(View.VISIBLE);
        }
    }

    public static Intent createIntent(Context context, String clanTag) {
        Intent i = new Intent(context, CreateClanActivity.class);
        i.putExtra(EXTRA_CLAN_TAG, clanTag);
        return i;
    }
}
