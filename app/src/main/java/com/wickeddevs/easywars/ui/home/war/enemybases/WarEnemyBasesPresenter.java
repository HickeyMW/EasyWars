package com.wickeddevs.easywars.ui.home.war.enemybases;

import android.util.Log;

import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.model.war.War;
import com.wickeddevs.easywars.data.service.contract.ClanService;
import com.wickeddevs.easywars.data.service.contract.WarService;

import javax.inject.Inject;

/**
 * Created by 375csptssce on 8/16/16.
 */
public class WarEnemyBasesPresenter implements WarEnemyBasesContract.ViewListener, WarService.LoadWarCallback {

    final static String TAG = "WarEnemyBasesPresenter";

    private WarEnemyBasesContract.View view;
    private WarService warService;
    private ClanService clanService;

    @Inject
    public WarEnemyBasesPresenter(WarService warService, ClanService clanService) {
        this.warService = warService;
        this.clanService = clanService;
    }

    @Override
    public void registerView(WarEnemyBasesContract.View activity) {
        view = activity;
    }

    @Override
    public void onCreate() {
        view.toggleLoading(true);
        warService.setLatestWarListener(this);
    }

    @Override
    public void onLoaded(War war) {
        view.toggleLoading(false);
        if (war == null) {
            Log.e(TAG, "onMemberLoaded: Should not load the view if no war");
        } else {
            view.displayEnemyBases(war);
        }
    }
}
