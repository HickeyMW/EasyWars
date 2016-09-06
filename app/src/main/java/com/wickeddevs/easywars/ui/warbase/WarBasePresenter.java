package com.wickeddevs.easywars.ui.warbase;

import java.util.ArrayList;

import com.wickeddevs.easywars.data.model.Clan;
import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.data.model.war.Comment;
import com.wickeddevs.easywars.data.service.contract.ClanService;
import com.wickeddevs.easywars.data.service.contract.UserService;
import com.wickeddevs.easywars.data.service.contract.WarService;
import com.wickeddevs.easywars.util.General;

/**
 * Created by 375csptssce on 8/22/16.
 */
public class WarBasePresenter implements WarBaseContract.ViewListener {

    private WarBaseContract.View view;

    private WarService warService;
    private ClanService clanService;
    private UserService userService;

    String warId;
    String baseId;
    boolean didClaim;

    public WarBasePresenter(WarService warService, ClanService clanService, UserService userService) {
        this.warService = warService;
        this.clanService = clanService;
        this.userService = userService;
    }

    @Override
    public void pressedClaim() {
        if (didClaim) {
            warService.removeClaim(warId, baseId);
            view.setButtonClaimText("Claim");
        } else {
            warService.claimBase(warId, baseId);
            view.setButtonClaimText("Unclaim");
        }
        didClaim = !didClaim;
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
                clanService.getClan(new ClanService.LoadClanCallback() {
                    @Override
                    public void onClanLoaded(Clan clan) {
                        view.displayBase(base);
                    }
                });
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
            public void newClaim(final String claim) {
                if (userService.isMyId(claim)) {
                    view.setButtonClaimText("Unclaim");
                }
                clanService.getClan(new ClanService.LoadClanCallback() {
                    @Override
                    public void onClanLoaded(Clan clan) {
                        view.addClaim(clan.members.get(claim).name);
                    }
                });
            }

            @Override
            public void removeClaim(final String claim) {
                clanService.getClan(new ClanService.LoadClanCallback() {
                    @Override
                    public void onClanLoaded(Clan clan) {
                        view.removeClaim(clan.members.get(claim).name);
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
