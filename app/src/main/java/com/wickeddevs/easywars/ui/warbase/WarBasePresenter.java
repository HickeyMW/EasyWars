package com.wickeddevs.easywars.ui.warbase;

import java.util.ArrayList;

import com.wickeddevs.easywars.data.model.Clan;
import com.wickeddevs.easywars.data.model.war.Attack;
import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.data.model.war.Comment;
import com.wickeddevs.easywars.data.service.contract.ClanService;
import com.wickeddevs.easywars.data.service.contract.UserService;
import com.wickeddevs.easywars.data.service.contract.WarService;
import com.wickeddevs.easywars.util.Shared;

import javax.inject.Inject;

/**
 * Created by 375csptssce on 8/22/16.
 */
public class WarBasePresenter implements WarBaseContract.ViewListener {

    private WarBaseContract.View view;

    private WarService warService;
    private ClanService clanService;

    private String warId;
    private int baseId;

    @Inject
    public WarBasePresenter(WarService warService, ClanService clanService) {
        this.warService = warService;
        this.clanService = clanService;
    }


    @Override
    public void sendComment(String body) {
        warService.addComment(body, warId, baseId);
    }

    @Override
    public void registerView(WarBaseContract.View activity) {
        view = activity;
    }

    @Override
    public void onCreate() {
        warId = view.getWarId();
        baseId = view.getBaseId();
        warService.setBaseListener(warId, baseId, new WarService.LoadBaseListener() {
            @Override
            public void onLoaded(final Base base) {
                view.displayBase(base);
            }

            @Override
            public void newComment(final Comment comment) {
                clanService.getClan(new ClanService.LoadClanCallback() {
                    @Override
                    public void onClanLoaded(Clan clan) {
                        comment.dateTime = Shared.formatDateTime(comment.timestamp);
                        comment.name = clan.members.get(comment.uid).name;
                        view.addComment(comment);
                    }
                });
            }

            @Override
            public void newClaim(final Attack attack) {
                clanService.getClan(new ClanService.LoadClanCallback() {
                    @Override
                    public void onClanLoaded(Clan clan) {
                        attack.name = clan.members.get(attack.uid).name;
                        view.addClaim(attack);
                    }
                });
            }

            @Override
            public void removeClaim(final Attack attack) {
                view.removeClaim(attack);
            }
        });
    }

    @Override
    public void onDestroy() {
        warService.removeBaseListener();
    }
}
