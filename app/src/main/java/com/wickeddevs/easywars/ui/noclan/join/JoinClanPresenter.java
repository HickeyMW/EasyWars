package com.wickeddevs.easywars.ui.noclan.join;

import android.util.Log;

import com.wickeddevs.easywars.data.model.JoinRequest;
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.data.service.contract.ApiService;
import com.wickeddevs.easywars.data.service.contract.JoinClanService;

import javax.inject.Inject;

/**
 * Created by hicke_000 on 8/3/2016.
 */
public class JoinClanPresenter implements JoinClanContract.ViewListener {

    final static String TAG = "CreateClanPresenter";

    private ApiClan apiClan;
    private String name;

    private JoinClanContract.View view;
    private ApiService apiService;
    private JoinClanService joinClanService;

    @Inject
    public JoinClanPresenter(ApiService apiService, JoinClanService joinClanService) {
        this.apiService = apiService;
        this.joinClanService = joinClanService;
    }

    @Override
    public void registerView(JoinClanContract.View activity) {
        view = activity;
    }

    @Override
    public void onCreate() {
        view.toggleLoading(true);
        apiService.getApiClan(view.getClanTag(), new ApiService.LoadApiClanCallback() {
            @Override
            public void onApiClanLoaded(ApiClan apiClan) {
                view.toggleLoading(false);
                JoinClanPresenter.this.apiClan = apiClan;
                view.displayClanInfo(apiClan);
            }
        });
    }

    @Override
    public void selectedName(String name) {
        this.name = name;
        view.allowJoin();
    }

    @Override
    public void requestJoin(String message) {
        joinClanService.setJoinRequest(apiClan.tag, new JoinRequest(name, message));
        view.navigateToVerifyJoinClanUi();
    }
}
