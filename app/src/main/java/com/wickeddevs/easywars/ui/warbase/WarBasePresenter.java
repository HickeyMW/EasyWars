package com.wickeddevs.easywars.ui.warbase;

import java.util.ArrayList;

import com.wickeddevs.easywars.data.model.Clan;
import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.data.model.war.Comment;
import com.wickeddevs.easywars.data.service.contract.ClanService;
import com.wickeddevs.easywars.data.service.contract.WarService;
import com.wickeddevs.easywars.util.General;

/**
 * Created by 375csptssce on 8/22/16.
 */
public class WarBasePresenter implements WarBaseContract.ViewListener {

    private WarBaseContract.View view;

    private WarService warService;
    private ClanService clanService;

    String warId;
    String baseId;
    boolean didClaim;

    public WarBasePresenter(ClanService clanService, WarService warService) {
        this.clanService = clanService;
        this.warService = warService;
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
    public void onAttach() {
        warId = view.getWarId();
        baseId = view.getBaseId();
        warService.setBaseListener(warId, baseId, new WarService.LoadBaseListener() {
            @Override
            public void onLoaded(final Base base) {
                didClaim = base.didClaim;
                if (didClaim) {
                    view.setButtonClaimText("Unclaim");
                }
                clanService.getClan(new ClanService.LoadClanCallback() {
                    @Override
                    public void onClanLoaded(Clan clan) {
                        for (Comment comment : base.comments) {
                            comment.dateTime = General.formatDateTime(comment.timestamp);
                            comment.name = clan.members.get(comment.uid).name;
                        }
                        ArrayList<String> names = new ArrayList<String>();
                        for (String claim : base.claims) {
                            names.add(clan.members.get(claim).name);
                        }
                        base.claims = names;
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
    public void onDetach() {
        warService.removeBaseListener();
    }
}
