package wickeddevs.easywars.ui.warbase;

import wickeddevs.easywars.data.model.war.Base;
import wickeddevs.easywars.data.model.war.Comment;
import wickeddevs.easywars.data.service.contract.WarService;
import wickeddevs.easywars.util.General;

/**
 * Created by 375csptssce on 8/22/16.
 */
public class WarBasePresenter implements WarBaseContract.ViewListener {

    private WarBaseContract.View view;

    private WarService warService;

    String warId;
    String baseId;
    boolean didClaim;

    public WarBasePresenter(WarService warService) {
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
            public void onLoaded(Base base) {
                didClaim = base.didClaim;
                if (didClaim) {
                    view.setButtonClaimText("Unclaim");
                }
                for (Comment comment : base.comments) {
                    comment.dateTime = General.formatDateTime(comment.timestamp);
                }
                view.displayBase(base);
            }
        });
    }

    @Override
    public void onDetach() {

    }
}
