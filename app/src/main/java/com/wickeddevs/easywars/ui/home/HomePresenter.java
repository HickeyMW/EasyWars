package com.wickeddevs.easywars.ui.home;

import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.model.User;
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.data.service.contract.ApiService;
import com.wickeddevs.easywars.data.service.contract.ClanService;
import com.wickeddevs.easywars.data.service.contract.UserService;

/**
 * Created by 375csptssce on 8/16/16.
 */
public class HomePresenter implements HomeContract.ViewListener {

    private final static String TAG = "HomePresenter";

    private HomeContract.View view;
    private ClanService clanService;
    private UserService userService;
    private ApiService apiService;

    public HomePresenter(ClanService clanService, UserService userService, ApiService apiService) {
        this.clanService = clanService;
        this.userService = userService;
        this.apiService = apiService;
    }

    @Override
    public void registerView(HomeContract.View activity) {
        view = activity;
    }

    @Override
    public void onAttach() {
        userService.getUser(new UserService.LoadUserCallback() {
            @Override
            public void onUserLoaded(User user) {
                apiService.getApiClan(user.clanTag, new ApiService.LoadApiClanCallback() {
                    @Override
                    public void onApiClanLoaded(final ApiClan apiClan) {
                        clanService.getSelf(new ClanService.LoadMemberCallback() {
                            @Override
                            public void onMemberLoaded(Member member) {
                                view.displayUi(member, apiClan);
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onDetach() {

    }
}
