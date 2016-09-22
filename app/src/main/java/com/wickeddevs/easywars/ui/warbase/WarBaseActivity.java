package com.wickeddevs.easywars.ui.warbase;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import javax.inject.Inject;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.recyclerview.ClaimCommentRVA;
import com.wickeddevs.easywars.miscellaneous.SpaceItemDecoration;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import com.wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.easywars.data.model.war.Attack;
import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.data.model.war.Comment;
import com.wickeddevs.easywars.databinding.ActivityWarBaseBinding;
import com.wickeddevs.easywars.util.Shared;

public class WarBaseActivity extends BasePresenterActivity<WarBaseContract.ViewListener> implements
        WarBaseContract.View {

    public final static String EXTRA_WAR_ID = "EXTRA_WAR_ID";
    public final static String EXTRA_BASE_ID = "EXTRA_BASE_ID";

    @Inject
    public WarBaseContract.ViewListener presenter;
    private ActivityWarBaseBinding binding;

    private ClaimCommentRVA claimCommentRVA;
    private LinearLayoutManager linearLayoutManager;
    private boolean displayingShadow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_war_base);
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = binding.etComment.getText().toString();
                presenter.sendComment(comment);
                binding.etComment.setText("");
            }
        });
        claimCommentRVA = new ClaimCommentRVA();
        binding.rvClaimsComments.setAdapter(claimCommentRVA);
        binding.rvClaimsComments.addItemDecoration(new SpaceItemDecoration());
        linearLayoutManager = new LinearLayoutManager(this);
        binding.rvClaimsComments.setLayoutManager(linearLayoutManager);
        binding.rvClaimsComments.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                checkDrawShadow();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void checkDrawShadow() {
        int friendlyMessageCount = claimCommentRVA.getItemCount();
        int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        if (lastVisiblePosition != friendlyMessageCount -1) {
            if (!displayingShadow) {
                displayingShadow = true;
                binding.layoutShadow.animate().alpha(1.0f).setDuration(300).start();
            }
        } else {
            if (displayingShadow) {
                displayingShadow = false;
                binding.layoutShadow.animate().alpha(0.0f).setDuration(300).start();
            }
        }
    }

    @Override
    protected WarBaseContract.ViewListener getPresenter() {
        if(presenter == null){
            DaggerViewInjectorComponent.builder()
                    .serviceComponent(DaggerServiceComponent.create())
                    .build().inject(this);
        }
        return presenter;
    }

    @Override
    public String getWarId() {
        return getIntent().getStringExtra(EXTRA_WAR_ID);
    }


    @Override
    public int getBaseId() {
        return getIntent().getIntExtra(EXTRA_BASE_ID, -1);
    }

    @Override
    public void displayBase(Base base) {
        int position = Integer.valueOf(base.key) + 1;
        binding.tvName.setText(position + ". " + base.name);
        binding.ivTownHall.setImageResource(Shared.getThResource(base.thLevel));
        checkDrawShadow();
    }

    @Override
    public void addClaim(Attack attackClaim) {
        claimCommentRVA.addClaim(attackClaim);
    }

    @Override
    public void removeClaim(Attack attackClaim) {
        claimCommentRVA.removeClaim(attackClaim);
    }

    @Override
    public void addComment(Comment comment) {
        claimCommentRVA.addComment(comment);
    }


    public static Intent createIntent(Context context, String warId, int baseId) {
        Intent i = new Intent(context, WarBaseActivity.class);
        i.putExtra(EXTRA_WAR_ID, warId);
        i.putExtra(EXTRA_BASE_ID, baseId);
        return i;
    }
}
