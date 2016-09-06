package com.wickeddevs.easywars.miscellaneous;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.wickeddevs.easywars.util.Shared;

/**
 * Created by 375csptssce on 9/6/16.
 */
public class CloseKeyboardDrawerListener implements DrawerLayout.DrawerListener {

    Activity activity;

    public CloseKeyboardDrawerListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        Shared.hideKeyboard(activity);
    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
