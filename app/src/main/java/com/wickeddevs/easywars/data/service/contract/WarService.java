package com.wickeddevs.easywars.data.service.contract;

import com.wickeddevs.easywars.data.model.war.Attack;
import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.data.model.war.Comment;
import com.wickeddevs.easywars.data.model.war.Participent;
import com.wickeddevs.easywars.data.model.war.War;
import com.wickeddevs.easywars.data.model.war.WarInfo;

import java.util.ArrayList;

/**
 * Created by 375csptssce on 8/16/16.
 */
public interface WarService {

    interface LoadWarCallback {
        void onLoaded(War war);
    }

    interface LoadOverviewCallback {
        void onLoaded(ArrayList<Participent> participents);
    }

    interface LoadAttacksCallback {
        void onLoaded(ArrayList<Attack> attacks);
    }

    interface ActiveWarCallback {
        void onLoaded(boolean isActive);
    }

    interface LoadBaseListener {
        void onLoaded(Base base);
        void newComment(Comment comment);
        void newClaim(Attack attack);
        void removeClaim(Attack attack);
    }

    void setBaseListener(String warId, int baseId, LoadBaseListener listener);

    void removeBaseListener();

    void getLatestWar(LoadWarCallback callback);

    void setLatestWarListener(LoadWarCallback listener);

    void getLatestWarOverview(LoadOverviewCallback callback);

    void setLatestWarOverviewListener(LoadOverviewCallback listener);

    void getLatestWarAttacks(LoadAttacksCallback callback);

    void getLatestWarAttacks(String participentKey, LoadAttacksCallback callback);

    void saveWarInfo(WarInfo warInfo);

    void saveBaseInfo(ArrayList<Base> bases);

    void startWar(ArrayList<Participent> participents);

    void deleteWar();

    void saveAttack(Attack attack);

    void deleteAttack(Attack attack);

    void addComment(String body, String warId, int baseId);

    void isActiveWar(ActiveWarCallback callback);
}
