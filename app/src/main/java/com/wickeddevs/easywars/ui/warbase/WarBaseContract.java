package com.wickeddevs.easywars.ui.warbase;

import java.util.ArrayList;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.war.Attack;
import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.data.model.war.Comment;

/**
 * Created by 375csptssce on 8/22/16.
 */
public interface WarBaseContract {

    interface View extends PView {

        String getWarId();

        int getBaseId();

        void setButtonClaimText(String text);

        void displayBase(Base base);

        void displayClaimsComments(ArrayList<String> claims, ArrayList<Comment> comments);

        void addClaim(Attack attackClaim);

        void addComment(Comment comment);

        void removeClaim(Attack attackClaim);
    }

    interface ViewListener extends Presenter<WarBaseContract.View> {

        void onCreate();

        void onDestroy();

        void pressedClaim();

        void sendComment(String body);
    }
}
