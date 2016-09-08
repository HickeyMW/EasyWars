package com.wickeddevs.easywars.ui.home.war.enemybases;

import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.model.war.War;
import com.wickeddevs.easywars.data.service.contract.ClanService;
import com.wickeddevs.easywars.data.service.contract.WarService;

import javax.inject.Inject;

/**
 * Created by 375csptssce on 8/16/16.
 */
public class WarEnemyBasesPresenter implements WarEnemyBasesContract.ViewListener {

    final static String TAG = "ChatPresenter";

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
