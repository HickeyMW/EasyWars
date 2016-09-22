package com.wickeddevs.easywars.ui.startwar.participents;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.recyclerview.ParticipentRVA;
import com.wickeddevs.easywars.adapters.recyclerview.ThSelectorRVA;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import com.wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.model.api.ApiMember;
import com.wickeddevs.easywars.databinding.ActivityWarParticipentsBinding;
import com.wickeddevs.easywars.util.Shared;

import java.util.ArrayList;

import javax.inject.Inject;

public class WarParticipentsActivity extends BasePresenterActivity<WarParticipentsContract.ViewListener> implements
        WarParticipentsContract.View {

    public final static String EXTRA_WAR_SIZE = "EXTRA_WAR_SIZE";

    @Inject
    public WarParticipentsContract.ViewListener presenter;

    private ActivityWarParticipentsBinding binding;

    private ParticipentRVA participentRVA;
    private AlertDialog dialogThSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_war_participents);
        binding.rvMembers.setLayoutManager(new LinearLayoutManager(this));
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
    protected WarParticipentsContract.ViewListener getPresenter() {
        if(presenter == null){
            DaggerViewInjectorComponent.builder()
                    .serviceComponent(DaggerServiceComponent.create())
                    .build().inject(this);
        }
        return presenter;
    }

    @Override
    public void removeMember(int position) {
        participentRVA.remove(position);
    }

    @Override
    public void undoRemoveMember() {
        participentRVA.undo();
    }

    @Override
    public void displayMemberList(ArrayList<Member> members, ArrayList<ApiMember> apiMembers) {
        participentRVA = new ParticipentRVA(members, apiMembers, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = binding.rvMembers.getChildLayoutPosition(view);
                presenter.selectedParticipent(participentRVA.getParticipent(position), position);
            }
        });
        binding.rvMembers.setAdapter(participentRVA);
    }

    @Override
    public void displayThSelector() {
        View dialoglayout = getLayoutInflater().inflate(R.layout.dialog_th_selector, null);
        final RecyclerView rvThSelector = (RecyclerView) dialoglayout.findViewById(R.id.rvThSelector);
        rvThSelector.setLayoutManager(new GridLayoutManager(this, 3));
        rvThSelector.setAdapter(new ThSelectorRVA(new View.OnClickListener() {
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
    public void displayMember(int place, String name, int thLevel) {
        binding.tvLastAddedNum.setText(place + ".");
        binding.tvLastAddedName.setText(name);
        binding.ivLastAdded.setImageResource(Shared.getThResource(thLevel));
    }

    @Override
    public int getWarSize() {
        return getIntent().getIntExtra(EXTRA_WAR_SIZE, -1);
    }

    @Override
    public void setRemainingText(String remainingText) {
        binding.tvRemaining.setText(remainingText);
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

    public static Intent createIntent(Context context, int warSize) {
        Intent i = new Intent(context, WarParticipentsActivity.class);
        i.putExtra(EXTRA_WAR_SIZE, warSize);
        return i;
    }
}
