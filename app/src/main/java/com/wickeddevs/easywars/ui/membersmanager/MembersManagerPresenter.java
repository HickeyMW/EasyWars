package com.wickeddevs.easywars.ui.membersmanager;

import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.service.contract.ClanService;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by 375csptssce on 9/22/16.
 */

public class MembersManagerPresenter implements MembersManagerContract.ViewListener {

    private MembersManagerContract.View view;
    private ClanService clanService;

    private Member member;

    @Inject
    public MembersManagerPresenter(ClanService clanService) {
        this.clanService = clanService;
    }

    @Override
    public void onCreate() {
        view.toggleLoading(true);
        clanService.getClanMembers(new ClanService.LoadClanMembersCallback() {
            @Override
            public void onMembersLoaded(ArrayList<Member> members) {
                view.toggleLoading(false);
                view.displayMembers(members);
            }
        });
    }

    @Override
    public void selectedMember(Member member) {
        this.member = member;
    }

    @Override
    public void toggledAdmin(boolean isAdmin) {
        if (member.admin != isAdmin) {
            member.admin = isAdmin;
            clanService.saveClanMember(member);
        }
    }

    @Override
    public void registerView(MembersManagerContract.View activity) {
        view = activity;
    }
}
