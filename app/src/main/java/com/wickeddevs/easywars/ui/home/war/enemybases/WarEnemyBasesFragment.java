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
import com.wickeddevs.easywars.adapters.WarBasesAdapter;
import com.wickeddevs.easywars.base.BasePresenterFragment;
import com.wickeddevs.easywars.dagger.Injector;
import com.wickeddevs.easywars.data.model.war.War;
import com.wickeddevs.easywars.databinding.FragmentWarEnemyBasesBinding;
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
        //binding.rvWarBases.addItemDecoration(new SpaceItemDecoration());
        presenter.onCreate();
        return binding.getRoot();
    }

    @Override
    protected WarEnemyBasesContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
        }
        return presenter;
    }

    @Override
    public void displayWar(final War war, boolean isAdmin) {
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
        binding.rvWarBases.setVisibility(View.INVISIBLE);
       // binding.btnDeleteWar.setVisibility(View.INVISIBLE);

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
