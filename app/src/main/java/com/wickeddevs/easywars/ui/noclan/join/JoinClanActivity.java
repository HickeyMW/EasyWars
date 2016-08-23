package com.wickeddevs.easywars.ui.noclan.join;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.ClanMembersAdapter;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.Injector;
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.databinding.ActivityJoinClanBinding;
import com.wickeddevs.easywars.ui.noclan.verifyjoin.VerifyJoinClanActivity;

public class JoinClanActivity extends BasePresenterActivity<JoinClanContract.ViewListener> implements JoinClanContract.View {

    public final static String EXTRA_CLAN_TAG = "EXTRA_CLAN_TAG";

    private ActivityJoinClanBinding binding;

    @Inject
    public JoinClanContract.ViewListener presenter;

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
    }

    @Override
    public void displayClanInfo(ApiClan apiClan) {
        binding.layoutMain.setVisibility(View.VISIBLE);
        binding.tvClanName.setText(apiClan.name);
        binding.tvClanTag.setText(apiClan.tag);
        Glide.with(this).load(apiClan.badgeUrls.medium).centerCrop().into(binding.ivBadge);
        binding.rvMembers.setAdapter(new ClanMembersAdapter(apiClan.getMemberNames(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClanMembersAdapter clanAdapter = (ClanMembersAdapter) binding.rvMembers.getAdapter();
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

    }

    public static Intent createIntent(Context context, String clanTag) {
        Intent i = new Intent(context, JoinClanActivity.class);
        i.putExtra(EXTRA_CLAN_TAG, clanTag);
        return i;
    }
}
