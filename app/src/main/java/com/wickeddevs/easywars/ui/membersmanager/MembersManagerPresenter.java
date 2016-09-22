package com.wickeddevs.easywars.ui.membersmanager;

import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.service.contract.ClanService;

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

    }

    @Override
    public void selectedMember(Member member) {
        this.member = member;
    }

    @Override
    public void toggledAdmin(boolean isAdmin) {
        this.member.admin = isAdmin;
    }

    @Override
    public void registerView(MembersManagerContract.View activity) {
        view = activity;
    }
}
