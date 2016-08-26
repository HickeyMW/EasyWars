package com.wickeddevs.easywars.ui.home.war;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.SpaceItemDecoration;
import com.wickeddevs.easywars.adapters.WarBasesAdapter;
import com.wickeddevs.easywars.base.BasePresenterFragment;
import com.wickeddevs.easywars.dagger.Injector;
import com.wickeddevs.easywars.data.model.war.War;
import com.wickeddevs.easywars.databinding.FragmentWarPlannerBinding;
import com.wickeddevs.easywars.ui.startwar.StartWarActivity;
import com.wickeddevs.easywars.ui.warbase.WarBaseActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class WarPlannerFragment extends BasePresenterFragment<WarPlannerContract.ViewListener>
        implements WarPlannerContract.View {

    @Inject
    public WarPlannerContract.ViewListener presenter;

    private FragmentWarPlannerBinding binding;

    public WarPlannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_war_planner, container, false);
        binding.btnNewWar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), StartWarActivity.class);
                startActivity(i);
            }
        });
        binding.btnDeleteWar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.pressedDeleteWar();
            }
        });
        binding.rvWarBases.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvWarBases.addItemDecoration(new SpaceItemDecoration());
        return binding.getRoot();
    }

    @Override
    protected WarPlannerContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
        }
        return presenter;
    }

    @Override
    public void displayWar(final War war, boolean isAdmin) {
        binding.btnNewWar.setVisibility(View.INVISIBLE);
        binding.tvNoWar.setText("");

        if (isAdmin) {
            binding.btnDeleteWar.setVisibility(View.VISIBLE);
        }
        binding.layoutHeader.setVisibility(View.VISIBLE);
        binding.tvTitle.setText("War against " + war.enemyName);
        binding.tvTimeRemaining.setText(String.valueOf(formattedTimeRemainging(war.startTime)));
        binding.rvWarBases.setVisibility(View.VISIBLE);
        binding.rvWarBases.setAdapter(new WarBasesAdapter(war.bases, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = binding.rvWarBases.getChildLayoutPosition(view);
                startActivity(WarBaseActivity.createIntent(getContext(), war.key, String.valueOf(position)));
            }
        }));
    }

    @Override
    public void displayNoCurrentWar(boolean isAdmin) {
        binding.layoutHeader.setVisibility(View.INVISIBLE);
        binding.rvWarBases.setVisibility(View.INVISIBLE);
        binding.btnDeleteWar.setVisibility(View.INVISIBLE);
        if (isAdmin) {
            binding.tvNoWar.setText("There is no war going on right now. Press the button below to start one");
            binding.btnNewWar.setVisibility(View.VISIBLE);
        } else {
            binding.tvNoWar.setText("There is no war going on right now. Please wait for an admin to start one");
        }
    }

    private String formattedTimeRemainging(long time) {
        long warStart = time;
        long elapsedTime = System.currentTimeMillis() - warStart;
        String timeUntil = "";
        if (elapsedTime > 86400000) {
            elapsedTime -= 86400000;
            timeUntil += "Time until war end: ";
        } else {
            timeUntil += "Time until war start: ";
        }
        long hours = elapsedTime / 3600000;
        long remainingHours = 23 - hours;
        long remainingMinutes = 60 - ((elapsedTime - (hours * 3600000)) / 60000);
        if (remainingHours < 0) {
            timeUntil +=  "0:00";
        } else if (remainingMinutes == 60) {
            timeUntil += (remainingHours + 1) + ":00";
        } else if (remainingMinutes < 10) {
            timeUntil += remainingHours + ":0" + remainingMinutes;
        } else {
            timeUntil += remainingHours+ ":" + remainingMinutes;
        }

        return timeUntil;
    }

    @Override
    public void toggleProgressBar(boolean loading) {

    }

    @Override
    public void displayMessage(String message) {

    }
}
