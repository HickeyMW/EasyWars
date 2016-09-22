package com.wickeddevs.easywars.ui.home.war.enemybases;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.recyclerview.WarBasesRVA;
import com.wickeddevs.easywars.base.BasePresenterFragment;
import com.wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import com.wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.easywars.data.model.war.War;
import com.wickeddevs.easywars.databinding.FragmentWarEnemyBasesBinding;
import com.wickeddevs.easywars.miscellaneous.DividerItemDecoration;
import com.wickeddevs.easywars.ui.warbase.WarBaseActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class WarEnemyBasesFragment extends BasePresenterFragment<WarEnemyBasesContract.ViewListener>
        implements WarEnemyBasesContract.View {


    @Inject
    public WarEnemyBasesContract.ViewListener presenter;

    private FragmentWarEnemyBasesBinding binding;

    public WarEnemyBasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_war_enemy_bases, container, false);
        binding.rvWarBases.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvWarBases.addItemDecoration(new DividerItemDecoration(getContext(), R.drawable.divider));
        presenter.onCreate();
        return binding.getRoot();
    }

    @Override
    protected WarEnemyBasesContract.ViewListener getPresenter() {
        if(presenter == null){
            DaggerViewInjectorComponent.builder()
                    .serviceComponent(DaggerServiceComponent.create())
                    .build().inject(this);
        }
        return presenter;
    }

    @Override
    public void displayEnemyBases(final War war) {
        binding.rvWarBases.setVisibility(View.VISIBLE);
        binding.rvWarBases.setAdapter(new WarBasesRVA(war.bases, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = binding.rvWarBases.getChildLayoutPosition(view);
                startActivity(WarBaseActivity.createIntent(getContext(), war.key, position));
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
