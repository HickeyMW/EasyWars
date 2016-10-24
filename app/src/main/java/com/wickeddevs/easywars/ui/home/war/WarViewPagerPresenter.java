package com.wickeddevs.easywars.ui.home.war;

import com.wickeddevs.easywars.data.model.war.War;
import com.wickeddevs.easywars.data.service.contract.WarService;
import com.wickeddevs.easywars.util.Shared;

import javax.inject.Inject;

/**
 * Created by 375csptssce on 9/6/16.
 */
public class WarViewPagerPresenter implements WarViewPagerContract.ViewListener, WarService.LoadWarCallback {

    private WarViewPagerContract.View view;
    private WarService warService;

    @Inject
    public WarViewPagerPresenter(WarService warService) {
        this.warService = warService;
    }

    @Override
    public void onCreate() {
        view.toggleLoading(true);
        warService.setLatestWarListener(this);
    }

    @Override
    public void onLoaded(War war) {
        view.toggleLoading(false);
        if (war != null) {
            if (war.warInfo != null) {
                long currentTime = System.currentTimeMillis();
                long twoDaysAgoMilis = currentTime - Shared.MILIS_IN_TWO_DAYS;
                if (war.warInfo.startTime > twoDaysAgoMilis) {
                    view.displayWarUi(war.isParticipent);
                    view.setTitle("War vs " + war.warInfo.enemyName);
                    view.setSubTitle(Shared.formattedTimeRemainging(war.warInfo.startTime));
                } else {
                    view.displayNoWarUi();
                }
            }

        } else {
            view.displayNoWarUi();
        }
    }

    @Override
    public void registerView(WarViewPagerContract.View activity) {
        view = activity;
    }
}
