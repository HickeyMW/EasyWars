package com.wickeddevs.easywars.ui.home.war;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.viewpager.ChatViewPagerAdapter;
import com.wickeddevs.easywars.adapters.viewpager.WarViewPagerAdapter;
import com.wickeddevs.easywars.base.BasePresenterFragment;
import com.wickeddevs.easywars.dagger.Injector;
import com.wickeddevs.easywars.databinding.FragmentWarViewPagerBinding;
import com.wickeddevs.easywars.ui.home.NavigationDrawerProvider;
import com.wickeddevs.easywars.ui.home.chat.ChatContract;

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

    boolean isAdmin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        isAdmin = getArguments().getBoolean(IS_ADMIN);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_war_view_pager, container, false);
        WarViewPagerAdapter warViewPagerAdapter = new WarViewPagerAdapter(getChildFragmentManager(), isAdmin);
        binding.viewPager.setAdapter(warViewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        navigationDrawerProvider.setupDrawer(binding.toolbar);
        return binding.getRoot();
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

    public static WarViewPagerFragment getInstance(boolean isAdmin) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_ADMIN, isAdmin);
        WarViewPagerFragment warViewPagerFragment = new WarViewPagerFragment();
        warViewPagerFragment.setArguments(bundle);
        return warViewPagerFragment;
    }

    @Override
    protected WarViewPagerContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
        }
        return presenter;
    }

    @Override
    public void displayUi(boolean isCurrentWar) {
        if (isCurrentWar) {

        } else {
            binding.toolbar.setTitle("War Planner");
            binding.tabLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void toggleLoading(boolean loading) {

    }
}
