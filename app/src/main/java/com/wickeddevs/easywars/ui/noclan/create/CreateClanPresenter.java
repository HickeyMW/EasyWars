package com.wickeddevs.easywars.ui.noclan.create;

import android.util.Log;

import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.data.service.contract.ApiService;
import com.wickeddevs.easywars.data.service.contract.CreateClanService;

import javax.inject.Inject;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class CreateClanPresenter implements CreateClanContract.ViewListener {

    final static String TAG = "CreateClanPresenter";

    private CreateClanContract.View view;
    private ApiService apiService;
    private CreateClanService createClanService;

    private ApiClan apiClan;
    private String name;
    private int thLevel = -1;

    @Inject
    public CreateClanPresenter(ApiService apiService, CreateClanService createClanService) {
        this.apiService = apiService;
        this.createClanService = createClanService;
    }

    @Override
    public void registerView(CreateClanContract.View activity) {
        view = activity;
    }

    @Override
    public void onCreate() {
        view.toggleLoading(true);
        apiService.getApiClan(view.getClanTag(), new ApiService.LoadApiClanCallback() {
            @Override
            public void onApiClanLoaded(ApiClan apiClan) {
                view.toggleLoading(false);
                CreateClanPresenter.this.apiClan = apiClan;
                view.displayClanInfo(apiClan);
            }
        });
    }

    @Override
    public void selectedName(String name) {
        this.name = name;
        if (name != null && thLevel != -1) {
            view.allowCreate();
        }
    }

    @Override
    public void selectedThLevel(int thLevel) {
        this.thLevel = thLevel;
        if (name != null && thLevel != -1) {
            view.allowCreate();
        }
    }

    @Override
    public void createClanRequest() {
        createClanService.setCreateRequest(name, thLevel, apiClan.tag);
        view.navigateToVerifyCreateClanUi();
    }


}
