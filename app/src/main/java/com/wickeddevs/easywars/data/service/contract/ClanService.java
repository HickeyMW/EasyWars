package com.wickeddevs.easywars.data.service.contract;

import com.wickeddevs.easywars.data.model.Clan;
import com.wickeddevs.easywars.data.model.Member;

/**
 * Created by 375csptssce on 7/28/16.
 */
public interface ClanService {

    interface LoadMemberCallback {

        void onMemberLoaded(Member member);
    }

    interface LoadClanCallback {

        void onClanLoaded(Clan clan);
    }

    void getSelf(LoadMemberCallback callback);

    void getMember(String uid, LoadMemberCallback callback);

    void getClan(LoadClanCallback callback);
}
