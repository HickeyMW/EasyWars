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
    public void onAttach() {
        warService.getLatestWar(new WarService.LoadWarCallback() {
            @Override
            public void onLoaded(War war) {
                if (war == null) {
                    clanService.getSelf(new ClanService.LoadMemberCallback() {
                        @Override
                        public void onMemberLoaded(Member member) {
                            view.displayNoCurrentWar(member.admin);
                        }
                    });
                } else {
                    view.displayWar(war);
                }
            }
        });
    }

    @Override
    public void onDetach() {

    }
}
