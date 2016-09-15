package com.wickeddevs.easywars.ui.home.war.clanoverview;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.recyclerview.ParticipentAttacksAdapter;
import com.wickeddevs.easywars.base.BasePresenterFragment;
import com.wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import com.wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.easywars.data.model.war.Participent;
import com.wickeddevs.easywars.databinding.FragmentClanOverviewBinding;
import com.wickeddevs.easywars.miscellaneous.DividerItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClanOverviewFragment extends BasePresenterFragment<ClanOverviewContract.ViewListener>
        implements ClanOverviewContract.View  {

    @Inject
    public ClanOverviewContract.ViewListener presenter;

    private FragmentClanOverviewBinding binding;

    public ClanOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_clan_overview, container, false);
        binding.rvClanOverview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvClanOverview.addItemDecoration(new DividerItemDecoration(getContext(), R.drawable.divider));
        presenter.onCreate();
        return binding.getRoot();
    }

    @Override
    protected ClanOverviewContract.ViewListener getPresenter() {
        if(presenter == null){
            DaggerViewInjectorComponent.builder()
                    .serviceComponent(DaggerServiceComponent.create())
                    .build().inject(this);
        }
        return presenter;
    }

    @Override
    public void displayOverview(ArrayList<Participent> participents) {
        binding.rvClanOverview.setVisibility(View.VISIBLE);
        binding.rvClanOverview.setAdapter(new ParticipentAttacksAdapter(participents, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = binding.rvClanOverview.getChildLayoutPosition(view);
                
            }
        }));
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
