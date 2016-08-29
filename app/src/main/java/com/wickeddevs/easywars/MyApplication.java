package com.wickeddevs.easywars;

import android.app.Application;

import com.wickeddevs.easywars.data.service.firebase.FbInfo;

/**
 * Created by 375csptssce on 8/23/16.
 */
public class MyApplication extends Application {

    public static final int MAJOR_VERSION = 0;
    public static final int MINOR_VERSION = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        FbInfo.syncUser();
    }
}