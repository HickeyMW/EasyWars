package com.wickeddevs.easywars.ui.warbase;

import java.util.ArrayList;

import com.wickeddevs.easywars.data.model.Clan;
import com.wickeddevs.easywars.data.model.war.Attack;
import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.data.model.war.Comment;
import com.wickeddevs.easywars.data.service.contract.ClanService;
import com.wickeddevs.easywars.data.service.contract.UserService;
import com.wickeddevs.easywars.data.service.contract.WarService;
import com.wickeddevs.easywars.util.General;

import javax.inject.Inject;

/**
 * Created by 375csptssce on 8/22/16.
 */
public class WarBasePresenter implements WarBaseContract.ViewListener {

    private WarBaseContract.View view;

    private WarService warService;
    private ClanService clanService;
    private UserService userService;

    private String warId;
    private int baseId;
    private Attack attackClaim;

    @Inject
    public WarBasePresenter(WarService warService, ClanService clanService, UserService userService) {
        this.warService = warService;
        this.clanService = clanService;
        this.userService = userService;
    }

    @Override
    public void pressedClaim() {
        if (attackClaim != null) {
            warService.removeClaim(warId, attackClaim);
            view.setButtonClaimText("Attack");
        } else {
            warService.claimBase(warId, baseId);
            view.setButtonClaimText("Unclaim");
        }
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
                        comment.dateTime = General.formatDateTime(comment.timestamp);
                        comment.name = clan.members.get(comment.uid).name;
                        view.addComment(comment);
                    }
                });
            }

            @Override
            public void newClaim(final Attack attack) {
                if (userService.isMyId(attack.uid)) {
                    attackClaim = attack;
                    view.setButtonClaimText("Unclaim");
                }
                clanService.getClan(new ClanService.LoadClanCallback() {
                    @Override
                    public void onClanLoaded(Clan clan) {
                        view.addClaim(clan.members.get(attack.key).name);
                    }
                });
            }

            @Override
            public void removeClaim(final Attack attack) {
                clanService.getClan(new ClanService.LoadClanCallback() {
                    @Override
                    public void onClanLoaded(Clan clan) {
                        view.removeClaim(clan.members.get(attack.key).name);
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        warService.removeBaseListener();
    }
}
