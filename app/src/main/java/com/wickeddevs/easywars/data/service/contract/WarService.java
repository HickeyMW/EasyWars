package com.wickeddevs.easywars.data.service.contract;

import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.data.model.war.Comment;
import com.wickeddevs.easywars.data.model.war.War;

/**
 * Created by 375csptssce on 8/16/16.
 */
public interface WarService {

    interface LoadWarCallback {
        void onLoaded(War war);
    }

    interface LoadBaseListener {
        void onLoaded(Base base);
        void newComment(Comment comment);
        void newClaim(String claim);
        void removeClaim(String claim);
    }

    void setBaseListener(String warId, String baseId, LoadBaseListener listener);

    void removeBaseListener();

    void getLatestWar(LoadWarCallback callback);

    void startWar(War war);

    void deleteWar();

    void claimBase(String warId, String baseId);

    void removeClaim(String warId, String baseId);

    void addComment(String body, String warId, String baseId);
}
