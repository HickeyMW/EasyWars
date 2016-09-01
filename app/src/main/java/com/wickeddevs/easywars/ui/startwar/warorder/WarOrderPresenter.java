package com.wickeddevs.easywars.ui.startwar.warorder;

import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.data.service.contract.ApiService;

/**
 * Created by 375csptssce on 8/22/16.
 */
public class WarOrderPresenter implements WarOrderContract.ViewListener {

    private WarOrderContract.View view;

    private ApiService apiService;

    public WarOrderPresenter(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void registerView(WarOrderContract.View activity) {
        view = activity;
    }

    @Override
    public void onCreate() {

    }
}
