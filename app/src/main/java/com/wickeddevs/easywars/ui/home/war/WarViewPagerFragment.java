package com.wickeddevs.easywars.ui.home.war;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.viewpager.WarViewPagerAdapter;
import com.wickeddevs.easywars.base.BasePresenterFragment;
import com.wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import com.wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.easywars.databinding.FragmentWarViewPagerBinding;
import com.wickeddevs.easywars.ui.home.NavigationDrawerProvider;
import com.wickeddevs.easywars.ui.startwar.info.WarInfoActivity;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class WarViewPagerFragment extends BasePresenterFragment<WarViewPagerContract.ViewListener> implements WarViewPagerContract.View {


    private static final String IS_ADMIN = "IS_ADMIN";

    @Inject
    public WarViewPagerContract.ViewListener presenter;

    FragmentWarViewPagerBinding binding;
    NavigationDrawerProvider navigationDrawerProvider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_war_view_pager, container, false);
//        navigationDrawerProvider.setupDrawer(binding.toolbar);
        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), WarInfoActivity.class);
                startActivity(i);
            }
        });
        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.home, menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationDrawerProvider) {
            navigationDrawerProvider = (NavigationDrawerProvider) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement NavigationDrawerProvider");
        }
    }

    @Override
    public void setTitle(String title) {
//        binding.toolbar.setTitle(title);
    }

    @Override
    public void setSubTitle(String subTitle) {
//        binding.toolbar.setSubtitle(subTitle);
    }

    @Override
    public boolean isAdmin() {
        return getArguments().getBoolean(IS_ADMIN);
    }

    @Override
    public void displayUi(boolean activeWar) {
//        if (activeWar) {
//            WarViewPagerAdapter warViewPagerAdapter = new WarViewPagerAdapter(getChildFragmentManager());
//            binding.viewPager.setAdapter(warViewPagerAdapter);
//            binding.tabLayout.setupWithViewPager(binding.viewPager);
//            binding.tabLayout.getTabAt(0).setText("Enemy Bases");
//            binding.tabLayout.getTabAt(1).setText("Clan Overview");
//            binding.tabLayout.setVisibility(View.VISIBLE);
//        } else {
//            binding.toolbar.setTitle("War Planner");
//            binding.cardView.setVisibility(View.VISIBLE);
//            if (isAdmin()) {
//                binding.tvNoWar.setText("There is no war going on right now. Press the button below to start one");
//                binding.btnCreate.setVisibility(View.VISIBLE);
//            } else {
//                binding.tvNoWar.setText("There is no war going on right now. Please wait for an admin to start one");
//                binding.btnCreate.setVisibility(View.GONE);
//            }
//        }
    }

    @Override
    protected WarViewPagerContract.ViewListener getPresenter() {
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
            binding.cardView.setVisibility(View.INVISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public static WarViewPagerFragment getInstance(boolean isAdmin) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_ADMIN, isAdmin);
        WarViewPagerFragment warViewPagerFragment = new WarViewPagerFragment();
        warViewPagerFragment.setArguments(bundle);
        return warViewPagerFragment;
    }
}
