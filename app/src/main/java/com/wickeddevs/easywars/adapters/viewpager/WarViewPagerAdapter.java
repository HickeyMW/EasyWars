package com.wickeddevs.easywars.adapters.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wickeddevs.easywars.ui.home.war.enemybases.WarEnemyBasesFragment;

/**
 * Created by 375csptssce on 9/6/16.
 */
public class WarViewPagerAdapter extends FragmentPagerAdapter {

    boolean isAdmin;

    public WarViewPagerAdapter(FragmentManager fm, boolean isAdmin) {
        super(fm);
        this.isAdmin = isAdmin;
    }

    @Override
    public Fragment getItem(int position) {
        return new WarEnemyBasesFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
