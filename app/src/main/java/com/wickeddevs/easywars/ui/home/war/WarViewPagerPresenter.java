package com.wickeddevs.easywars.ui.home.war;

import com.wickeddevs.easywars.data.model.war.War;
import com.wickeddevs.easywars.data.service.contract.WarService;
import com.wickeddevs.easywars.util.Shared;

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
            view.toggleLoading(true);
            warService.isActiveWar(new WarService.ActiveWarCallback() {
                @Override
                public void onLoaded(boolean isActive) {
                    warLoaded = isActive;
                    if (warLoaded) {
                        warService.getLatestWar(new WarService.LoadWarCallback() {
                            @Override
                            public void onLoaded(War war) {
                                view.toggleLoading(false);
                                view.displayUi(true);
                                view.setTitle("War against " + war.warInfo.enemyName);
                                view.setSubTitle(Shared.formattedTimeRemainging(war.warInfo.startTime));
                            }
                        });
                    } else {
                        view.toggleLoading(false);
                        view.displayUi(false);
                    }

                }
            });
        }
    }

    @Override
    public void registerView(WarViewPagerContract.View activity) {
        view = activity;
    }
}
