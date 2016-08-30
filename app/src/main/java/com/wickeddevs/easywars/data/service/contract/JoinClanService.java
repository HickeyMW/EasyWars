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

    void getJoinRequests(JoinRequestsCallback callback);

    void setDecision(JoinRequest joinRequest, boolean approved);

    void searchJoinableClans(String query, ClanTagsCallback callback);

    interface DecisionListener {
        void onUpdate(JoinDecision joinDecision);
    }

    interface JoinRequestsCallback {
        void onLoaded(ArrayList<JoinRequest> joinRequests);
    }

    interface ClanTagsCallback {
        void onLoaded(ArrayList<String> clanTags);
    }
}