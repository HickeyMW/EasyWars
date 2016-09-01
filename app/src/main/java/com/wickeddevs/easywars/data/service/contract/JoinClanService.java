package com.wickeddevs.easywars.data.service.contract;

import java.util.ArrayList;

import com.wickeddevs.easywars.data.model.JoinDecision;
import com.wickeddevs.easywars.data.model.JoinRequest;

/**
 * Created by 375csptssce on 7/28/16.
 */
public interface JoinClanService {

    void setDecisionListener(DecisionListener listener);

    void removeJoinRequest();

    void setJoinRequest(String clanTag, JoinRequest joinRequest);

    void setJoinRequestListener(JoinRequestListener listener);

    void setDecision(JoinRequest joinRequest, boolean approved);

    void searchJoinableClans(String query, ClanTagsCallback callback);

    interface DecisionListener {
        void onUpdate(JoinDecision joinDecision);
    }

    interface JoinRequestListener {
        void intialLoadComplete();
        void addJoinRequest(JoinRequest joinRequest);
    }

    interface ClanTagsCallback {
        void onLoaded(ArrayList<String> clanTags);
    }
}
