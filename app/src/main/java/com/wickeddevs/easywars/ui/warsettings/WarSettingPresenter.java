package com.wickeddevs.easywars.ui.warsettings;

import com.wickeddevs.easywars.data.model.Clan;
import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.data.model.war.Participent;
import com.wickeddevs.easywars.data.model.war.War;
import com.wickeddevs.easywars.data.service.contract.ClanService;
import com.wickeddevs.easywars.data.service.contract.WarService;
import com.wickeddevs.easywars.ui.attacks.AttacksContract;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by 375csptssce on 9/22/16.
 */

public class WarSettingPresenter implements WarSettingsContract.ViewListener {

    private WarSettingsContract.View view;
    private WarService warService;
    private ClanService clanService;

    @Inject
    public WarSettingPresenter(WarService warService, ClanService clanService) {
        this.warService = warService;
        this.clanService = clanService;
    }

    @Override
    public void onCreate() {
        view.toggleLoading(true);
        warService.getLatestWarOverview(new WarService.LoadOverviewCallback() {
            @Override
            public void onLoaded(final ArrayList<Participent> participents) {
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
                            view.displayParticipents(participents);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void deleteWar() {
        warService.deleteWar();
    }

    @Override
    public void registerView(WarSettingsContract.View activity) {
        view = activity;
    }
}
