package com.wickeddevs.easywars.ui.warbase;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;

import javax.inject.Inject;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.ClaimCommentAdapter;
import com.wickeddevs.easywars.adapters.SpaceItemDecoration;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.Injector;
import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.data.model.war.Comment;
import com.wickeddevs.easywars.databinding.ActivityWarBaseBinding;
import com.wickeddevs.easywars.util.Shared;

public class WarBaseActivity extends BasePresenterActivity<WarBaseContract.ViewListener> implements
        WarBaseContract.View {

    public final static String EXTRA_WAR_ID = "EXTRA_WAR_ID";
    public final static String EXTRA_BASE_ID = "EXTRA_BASE_ID";

    private ClaimCommentAdapter claimCommentAdapter;

    @Inject
    public WarBaseContract.ViewListener presenter;

    private ActivityWarBaseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_war_base);
        binding.btnClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.pressedClaim();
            }
        });
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = binding.etComment.getText().toString();
                presenter.sendComment(comment);
                binding.etComment.setText("");
            }
        });
        binding.rvClaimsComments.addItemDecoration(new SpaceItemDecoration());
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected WarBaseContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
        }
        return presenter;
    }

    @Override
    public String getWarId() {
        return getIntent().getStringExtra(EXTRA_WAR_ID);
    }

    @Override
    public String getBaseId() {
        return getIntent().getStringExtra(EXTRA_BASE_ID);
    }

    @Override
    public void setButtonClaimText(String text) {
        binding.btnClaim.setText(text);
    }

    @Override
    public void displayBase(Base base) {
        binding.tvName.setText(base.name);
        binding.ivTownHall.setImageResource(Shared.getThResource(base.townHallLevel));
        binding.rvClaimsComments.setLayoutManager(new LinearLayoutManager(this));
        claimCommentAdapter = new ClaimCommentAdapter(base.claims, base.comments);
        binding.rvClaimsComments.setAdapter(claimCommentAdapter);
    }

    @Override
    public void displayClaimsComments(ArrayList<String> claims, ArrayList<Comment> comments) {

    }

    @Override
    public void addClaim(String claim) {
        claimCommentAdapter.addClaim(claim);
    }

    @Override
    public void addComment(Comment comment) {
        claimCommentAdapter.addComment(comment);
    }

    @Override
    public void removeClaim(String claim) {
        claimCommentAdapter.removeClaim(claim);
    }

    public static Intent createIntent(Context context, String warId, String baseId) {
        Intent i = new Intent(context, WarBaseActivity.class);
        i.putExtra(EXTRA_WAR_ID, warId);
        i.putExtra(EXTRA_BASE_ID, baseId);
        return i;
    }
}
