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
        warService.loadBase(warId, baseId, new WarService.LoadBaseCallback() {
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
        });
    }

    @Override
    public void onDetach() {

    }
}
