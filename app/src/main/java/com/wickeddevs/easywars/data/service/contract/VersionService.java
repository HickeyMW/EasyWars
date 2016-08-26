package com.wickeddevs.easywars.data.service.contract;

/**
 * Created by 375csptssce on 8/26/16.
 */
public interface VersionService {

    void getCurrentVersion(CheckVersionCallback callback);

    interface CheckVersionCallback {
        void onVersionLoaded(int major, int minor);
    }
}
