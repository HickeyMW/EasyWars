package com.wickeddevs.easywars.adapters.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wickeddevs.easywars.ui.home.chat.ChatFragment;

/**
 * Created by 375csptssce on 9/6/16.
 */
public class ChatVPA extends FragmentPagerAdapter {

    boolean isAdmin;

    public ChatVPA(FragmentManager fm, boolean isAdmin) {
        super(fm);
        this.isAdmin = isAdmin;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ChatFragment.getInstance(false);
        }
        return ChatFragment.getInstance(true);
    }

    @Override
    public int getCount() {
        if (isAdmin) {
            return 2;
        }
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Member Chat";
        }
        return "Admin Chat";
    }
}
