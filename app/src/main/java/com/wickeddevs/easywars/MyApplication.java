package com.wickeddevs.easywars;

import android.app.Application;

import com.wickeddevs.easywars.data.service.firebase.FbInfo;

/**
 * Created by 375csptssce on 8/23/16.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FbInfo.syncUser();
    }
}