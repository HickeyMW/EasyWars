package com.wickeddevs.easywars.data.service.contract;

import com.wickeddevs.easywars.data.model.Clan;
import com.wickeddevs.easywars.data.model.Member;

import java.util.ArrayList;

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

    interface LoadClanMembersCallback {

        void onMembersLoaded(ArrayList<Member> members);
    }

    void getSelf(LoadMemberCallback callback);

    void getMember(String uid, LoadMemberCallback callback);

    void getClan(LoadClanCallback callback);

    void getClanMembers(LoadClanMembersCallback callback);
}
