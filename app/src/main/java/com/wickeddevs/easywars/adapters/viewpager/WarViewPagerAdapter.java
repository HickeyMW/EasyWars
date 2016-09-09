package com.wickeddevs.easywars.adapters.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wickeddevs.easywars.ui.home.war.clanoverview.ClanOverviewFragment;
import com.wickeddevs.easywars.ui.home.war.enemybases.WarEnemyBasesFragment;

/**
 * Created by 375csptssce on 9/6/16.
 */
public class WarViewPagerAdapter extends FragmentPagerAdapter {

    public WarViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new WarEnemyBasesFragment();
        }
        return new ClanOverviewFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
