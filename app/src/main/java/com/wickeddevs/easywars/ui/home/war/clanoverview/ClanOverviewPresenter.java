package com.wickeddevs.easywars.ui.home.war.clanoverview;

import com.wickeddevs.easywars.data.model.Clan;
import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.model.war.Participent;
import com.wickeddevs.easywars.data.service.contract.ClanService;
import com.wickeddevs.easywars.data.service.contract.WarService;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by 375csptssce on 9/9/16.
 */
public class ClanOverviewPresenter implements ClanOverviewContract.ViewListener, WarService.LoadOverviewCallback {

    private ClanOverviewContract.View view;
    private WarService warService;
    private ClanService clanService;

    @Inject
    public ClanOverviewPresenter(WarService warService, ClanService clanService) {
        this.warService = warService;
        this.clanService = clanService;
    }

    @Override
    public void onCreate() {
        view.toggleLoading(true);
        warService.setLatestWarOverviewListener(this);
    }

    @Override
    public void onLoaded(final ArrayList<Participent> participents) {
        if (participents != null) {
            clanService.getClan(new ClanService.LoadClanCallback() {
                @Override
                public void onClanLoaded(Clan clan) {
                    for (Participent participent : participents) {
                        if (clan.members.containsKey(participent.uid)) {
                            Member member = clan.members.get(participent.uid);
                            participent.name = member.name;
                            participent.thLevel = member.thLevel;
                        }
                        view.toggleLoading(false);
                        view.displayOverview(participents);
                    }
                }
            });
        }

    }

    @Override
    public void registerView(ClanOverviewContract.View activity) {
        this.view = activity;
    }
}
