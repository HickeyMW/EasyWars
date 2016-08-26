package com.wickeddevs.easywars.ui.warbase;

import java.util.ArrayList;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.data.model.war.Claim;
import com.wickeddevs.easywars.data.model.war.Comment;

/**
 * Created by 375csptssce on 8/22/16.
 */
public interface WarBaseContract {

    interface View extends PView {

        String getWarId();

        String getBaseId();

        void setButtonClaimText(String text);

        void displayBase(Base base);

        void displayClaimsComments(ArrayList<String> claims, ArrayList<Comment> comments);

        void addClaim(String claim);

        void addComment(Comment comment);

        void removeClaim(String claim);
    }

    interface ViewListener extends Presenter<WarBaseContract.View> {

        void pressedClaim();

        void sendComment(String body);
    }
}
