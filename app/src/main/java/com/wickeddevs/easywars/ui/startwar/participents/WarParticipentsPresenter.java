package com.wickeddevs.easywars.ui.startwar.participents;

import com.wickeddevs.easywars.data.model.Clan;
import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.model.User;
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.data.model.war.Participent;
import com.wickeddevs.easywars.data.service.contract.ApiService;
import com.wickeddevs.easywars.data.service.contract.ClanService;
import com.wickeddevs.easywars.data.service.contract.UserService;
import com.wickeddevs.easywars.data.service.contract.WarService;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by 375csptssce on 9/12/16.
 */
public class WarParticipentsPresenter implements WarParticipentsContract.ViewListener {

    private WarParticipentsContract.View view;

    private ApiService apiService;
    private WarService warService;
    private UserService userService;
    private ClanService clanService;

    private Participent participent;
    private int position;
    private int remaining;
    private ArrayList<Participent> participents = new ArrayList<>();

    @Inject
    public WarParticipentsPresenter(ApiService apiService, WarService warService, UserService userService, ClanService clanService) {
        this.apiService = apiService;
        this.warService = warService;
        this.userService = userService;
        this.clanService = clanService;
    }




    @Override
    public void onCreate() {
        remaining = view.getWarSize();
        view.toggleLoading(true);
        userService.getUser(new UserService.LoadUserCallback() {
            @Override
            public void onUserLoaded(User user) {
                apiService.getApiClan(user.clanTag, new ApiService.LoadApiClanCallback() {
                    @Override
                    public void onApiClanLoaded(final ApiClan apiClan) {
                        clanService.getClan(new ClanService.LoadClanCallback() {
                            @Override
                            public void onClanLoaded(Clan clan) {
                                view.toggleLoading(false);
                                ArrayList<Member> members = new ArrayList<Member>(clan.members.values());
                                view.displayMemberList(members, apiClan.memberList);
                            }
                        });
                    }
                });
            }
        });

    }

    @Override
    public void selectedParticipent(Participent participent, int position) {
        if (remaining > 0) {
            this.position = position;
            this.participent = participent;
            if (participent.uid == null) {
                view.displayThSelector();
            } else {
                addParticipent();
            }
        }
    }

    @Override
    public void selectedTownHall(int thLevel) {
        participent.thLevel = thLevel;
        addParticipent();
    }

    private void addParticipent() {
        participents.add(participent);
        view.removeMember(position);
        view.displayMember(participents.size(), participent.name, participent.thLevel);
        view.setRemainingText(String.valueOf(--remaining));
        if (remaining == 0) {
            view.allowDone(true);
        }
    }

    @Override
    public void pressedUndo() {
        view.undoRemoveMember();
        participents.remove(participents.size() - 1);
        view.allowDone(false);
    }

    @Override
    public void pressedDone() {
        warService.startWar(participents);
        view.dismiss();
    }

    @Override
    public void registerView(WarParticipentsContract.View activity) {
        view = activity;
    }
}
