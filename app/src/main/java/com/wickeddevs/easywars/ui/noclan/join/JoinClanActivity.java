package com.wickeddevs.easywars.ui.noclan.join;

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
import com.wickeddevs.easywars.databinding.ActivityJoinClanBinding;
import com.wickeddevs.easywars.ui.noclan.verifyjoin.VerifyJoinClanActivity;
import com.wickeddevs.easywars.util.Shared;

public class JoinClanActivity extends BasePresenterActivity<JoinClanContract.ViewListener> implements JoinClanContract.View {

    public final static String EXTRA_CLAN_TAG = "EXTRA_CLAN_TAG";

    @Inject
    public JoinClanContract.ViewListener presenter;
    private ActivityJoinClanBinding binding;

    private AlertDialog dialogThSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_clan);
        binding.rvMembers.setLayoutManager(new LinearLayoutManager(this));
        binding.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.requestJoin(binding.etMessage.getText().toString());
            }
        });
        binding.layoutThLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialoglayout = getLayoutInflater().inflate(R.layout.dialog_th_selector, null);
                final RecyclerView rvThSelector = (RecyclerView) dialoglayout.findViewById(R.id.rvThSelector);
                rvThSelector.setLayoutManager(new GridLayoutManager(JoinClanActivity.this, 3));
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
                AlertDialog.Builder builder = new AlertDialog.Builder(JoinClanActivity.this);
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
        binding.tvClanTag.setText(apiClan.tag);
        Glide.with(this).load(apiClan.badgeUrls.medium).centerCrop().into(binding.ivBadge);
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
    public void allowJoin() {
        binding.btnJoin.setVisibility(View.VISIBLE);
    }

    @Override
    public String getClanTag() {
        return getIntent().getStringExtra(EXTRA_CLAN_TAG);
    }

    @Override
    public void navigateToVerifyJoinClanUi() {
        Intent i = new Intent(this, VerifyJoinClanActivity.class);
        startActivity(i);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected JoinClanContract.ViewListener getPresenter() {
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

    public static Intent createIntent(Context context, String clanTag) {
        Intent i = new Intent(context, JoinClanActivity.class);
        i.putExtra(EXTRA_CLAN_TAG, clanTag);
        return i;
    }
}
