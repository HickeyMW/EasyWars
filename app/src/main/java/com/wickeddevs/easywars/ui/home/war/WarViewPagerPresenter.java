package com.wickeddevs.easywars.ui.home.war;

import com.wickeddevs.easywars.data.model.war.War;
import com.wickeddevs.easywars.data.service.contract.WarService;
import com.wickeddevs.easywars.util.Shared;

import javax.inject.Inject;

/**
 * Created by 375csptssce on 9/6/16.
 */
public class WarViewPagerPresenter implements WarViewPagerContract.ViewListener {

    private WarViewPagerContract.View view;
    private WarService warService;

    @Inject
    public WarViewPagerPresenter(WarService warService) {
        this.warService = warService;
    }

    @Override
    public void onCreate() {
        view.toggleLoading(true);
        warService.getLatestWar(new WarService.LoadWarCallback() {
            @Override
            public void onLoaded(War war) {
                if (war != null) {
                    view.toggleLoading(false);
                    view.displayWarUi(war.isParticipent);
                    view.setTitle("War vs " + war.warInfo.enemyName);
                    view.setSubTitle(Shared.formattedTimeRemainging(war.warInfo.startTime));
                } else {
                    view.toggleLoading(false);
                    view.displayNoWarUi();
                }
            }
        });
        warService.isActiveWar(new WarService.ActiveWarCallback() {
            @Override
            public void onLoaded(boolean isActive) {


            }
        });
    }

    @Override
    public void registerView(WarViewPagerContract.View activity) {
        view = activity;
    }
}
