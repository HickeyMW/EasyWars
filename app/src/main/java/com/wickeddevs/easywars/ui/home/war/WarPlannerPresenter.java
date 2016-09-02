package com.wickeddevs.easywars.ui.home.war;

import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.model.war.War;
import com.wickeddevs.easywars.data.service.contract.ClanService;
import com.wickeddevs.easywars.data.service.contract.WarService;

/**
 * Created by 375csptssce on 8/16/16.
 */
public class WarPlannerPresenter implements WarPlannerContract.ViewListener {

    final static String TAG = "ChatPresenter";

    private WarPlannerContract.View view;
    private WarService warService;
    private ClanService clanService;

    public WarPlannerPresenter(WarService warService, ClanService clanService) {
        this.warService = warService;
        this.clanService = clanService;
    }

    @Override
    public void registerView(WarPlannerContract.View activity) {
        view = activity;
    }

    @Override
    public void onCreate() {
        view.toggleLoading(true);
        warService.getLatestWar(new WarService.LoadWarCallback() {
            @Override
            public void onLoaded(final War war) {
                clanService.getSelf(new ClanService.LoadMemberCallback() {
                    @Override
                    public void onMemberLoaded(Member member) {
                        view.toggleLoading(false);
                        if (war == null) {
                            view.displayNoCurrentWar(member.admin);
                        } else {
                            view.displayWar(war, member.admin);
                        }
                    }
                });

            }
        });
    }

    @Override
    public void pressedDeleteWar() {
        warService.deleteWar();
        clanService.getSelf(new ClanService.LoadMemberCallback() {
            @Override
            public void onMemberLoaded(Member member) {
                view.displayNoCurrentWar(member.admin);
            }
        });
    }
}
