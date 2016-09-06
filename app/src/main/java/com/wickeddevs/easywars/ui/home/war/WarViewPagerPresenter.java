package com.wickeddevs.easywars.ui.home.war;

import com.wickeddevs.easywars.data.service.contract.WarService;
import com.wickeddevs.easywars.data.service.firebase.FbWarService;

/**
 * Created by 375csptssce on 9/6/16.
 */
public class WarViewPagerPresenter implements WarViewPagerContract.ViewListener {

    private WarViewPagerContract.View view;
    private WarService warService;
    private boolean warLoaded = false;

    public WarViewPagerPresenter(WarService warService) {
        this.warService = warService;
    }

    @Override
    public void onResume() {
        if (!warLoaded) {
            warService.isActiveWar(new WarService.ActiveWarCallback() {
                @Override
                public void onLoaded(boolean isActive) {
                    warLoaded = isActive;
                    view.displayUi(isActive);
                }
            });
        }
    }

    @Override
    public void registerView(WarViewPagerContract.View activity) {
        view = activity;
    }
}
