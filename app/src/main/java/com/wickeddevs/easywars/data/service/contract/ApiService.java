package com.wickeddevs.easywars.data.service.contract;

import java.util.ArrayList;

import com.wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by 375csptssce on 7/28/16.
 */
public interface ApiService {

    interface LoadApiClanCallback {

        void onApiClanLoaded(ApiClan apiClan);
    }

    interface SearchApiClansCallback {

        void onApiClansLoaded(ArrayList<ApiClan> apiClans);
    }

    void getApiClan(String tag, LoadApiClanCallback callback);

    void searchClans(String query, SearchApiClansCallback callback);

    void getJoinableClans(LoadApiClanCallback callback);
}
