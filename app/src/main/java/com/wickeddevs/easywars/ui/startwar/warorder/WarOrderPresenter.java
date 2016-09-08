package com.wickeddevs.easywars.ui.startwar.warorder;

import android.util.Log;

import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.data.service.contract.ApiService;
import com.wickeddevs.easywars.data.service.contract.WarService;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by 375csptssce on 8/22/16.
 */
public class WarOrderPresenter implements WarOrderContract.ViewListener {

    private WarOrderContract.View view;

    private ApiService apiService;
    private WarService warService;

    private String name;
    private int position;
    private int remaining;
    private ArrayList<Base> bases = new ArrayList<>();

    @Inject
    public WarOrderPresenter(ApiService apiService, WarService warService) {
        this.apiService = apiService;
        this.warService = warService;
    }

    @Override
    public void registerView(WarOrderContract.View activity) {
        view = activity;
    }

    @Override
    public void onCreate() {
        remaining = view.getWarSize();
        view.toggleLoading(true);
        apiService.getApiClan(view.getClanTag(), new ApiService.LoadApiClanCallback() {
            @Override
            public void onApiClanLoaded(ApiClan apiClan) {
                view.toggleLoading(false);
                view.displayApiClan(apiClan);
                view.setRemainingText(String.valueOf(remaining));
            }
        });
    }

    @Override
    public void selectedName(String name, int position) {
        if (remaining > 0) {
            this.position = position;
            this.name = name;
            view.displayThSelector();
        }
    }

    @Override
    public void selectedTownHall(int thLevel) {
        bases.add(new Base(name, thLevel));
        view.removeMember(position);
        view.displayMember(name, thLevel);
        view.setRemainingText(String.valueOf(--remaining));
        if (remaining == 0) {
            view.allowDone(true);
        }
    }

    @Override
    public void pressedUndo() {
        view.undoRemoveMember();
        view.allowDone(false);
    }

    @Override
    public void pressedDone() {
        warService.startWar(bases);
        view.dismiss();
    }
}
