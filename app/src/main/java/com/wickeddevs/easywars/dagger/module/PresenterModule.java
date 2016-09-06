package com.wickeddevs.easywars.dagger.module;

import dagger.Module;
import dagger.Provides;
import com.wickeddevs.easywars.dagger.scope.ActivityScope;
import com.wickeddevs.easywars.data.service.contract.ApiService;
import com.wickeddevs.easywars.data.service.contract.ChatService;
import com.wickeddevs.easywars.data.service.contract.ClanService;
import com.wickeddevs.easywars.data.service.contract.CreateClanService;
import com.wickeddevs.easywars.data.service.contract.JoinClanService;
import com.wickeddevs.easywars.data.service.contract.UserService;
import com.wickeddevs.easywars.data.service.contract.VersionService;
import com.wickeddevs.easywars.data.service.contract.WarService;
import com.wickeddevs.easywars.ui.home.HomeContract;
import com.wickeddevs.easywars.ui.home.HomePresenter;
import com.wickeddevs.easywars.ui.home.chat.ChatContract;
import com.wickeddevs.easywars.ui.home.chat.ChatPresenter;
import com.wickeddevs.easywars.ui.home.war.WarViewPagerContract;
import com.wickeddevs.easywars.ui.home.war.WarViewPagerPresenter;
import com.wickeddevs.easywars.ui.home.war.enemybases.WarEnemyBasesContract;
import com.wickeddevs.easywars.ui.home.war.enemybases.WarEnemyBasesPresenter;
import com.wickeddevs.easywars.ui.joinrequests.JoinRequestsContract;
import com.wickeddevs.easywars.ui.joinrequests.JoinRequestsPresenter;
import com.wickeddevs.easywars.ui.loadingsplash.LoadingSplashContract;
import com.wickeddevs.easywars.ui.loadingsplash.LoadingSplashPresenter;
import com.wickeddevs.easywars.ui.noclan.create.CreateClanContract;
import com.wickeddevs.easywars.ui.noclan.create.CreateClanPresenter;
import com.wickeddevs.easywars.ui.noclan.verifycreate.VerifyCreateClanContract;
import com.wickeddevs.easywars.ui.noclan.verifycreate.VerifyCreateClanPresenter;
import com.wickeddevs.easywars.ui.noclan.join.JoinClanContract;
import com.wickeddevs.easywars.ui.noclan.join.JoinClanPresenter;
import com.wickeddevs.easywars.ui.noclan.verifyjoin.VerifyJoinClanContract;
import com.wickeddevs.easywars.ui.noclan.verifyjoin.VerifyJoinClanPresenter;
import com.wickeddevs.easywars.ui.shared.search.SearchClansContract;
import com.wickeddevs.easywars.ui.shared.search.SearchClansPresenter;
import com.wickeddevs.easywars.ui.startwar.basicinfo.BasicWarInfoContract;
import com.wickeddevs.easywars.ui.startwar.basicinfo.BasicWarInfoPresenter;
import com.wickeddevs.easywars.ui.startwar.warorder.WarOrderContract;
import com.wickeddevs.easywars.ui.startwar.warorder.WarOrderPresenter;
import com.wickeddevs.easywars.ui.warbase.WarBaseContract;
import com.wickeddevs.easywars.ui.warbase.WarBasePresenter;

/**
 * Created by hicke_000 on 8/2/2016.
 */
@Module
public class PresenterModule {

    @Provides
    @ActivityScope
    LoadingSplashContract.ViewListener providesLoadingSplashPresenter(UserService userService, VersionService versionService) {
        return new LoadingSplashPresenter(userService, versionService);
    }

    @Provides
    @ActivityScope
    ChatContract.ViewListener providesChatContractPresenter(ChatService chatService, ClanService clanService) {
        return new ChatPresenter(chatService, clanService);
    }

    @Provides
    @ActivityScope
    SearchClansContract.ViewListener providesSearchClansPresenter(ApiService apiService, JoinClanService joinClanService) {
        return new SearchClansPresenter(apiService, joinClanService);
    }

    @Provides
    @ActivityScope
    CreateClanContract.ViewListener providesCreateClanPresenter(ApiService apiService, CreateClanService createClanService) {
        return new CreateClanPresenter(apiService, createClanService);
    }

    @Provides
    @ActivityScope
    JoinClanContract.ViewListener providesJoinClanPresenter(ApiService apiService, JoinClanService joinClanService) {
        return new JoinClanPresenter(apiService, joinClanService);
    }

    @Provides
    @ActivityScope
    VerifyCreateClanContract.ViewListener providesCreatingClanPresenter(ApiService apiService, CreateClanService createClanService) {
        return new VerifyCreateClanPresenter(apiService,createClanService);
    }

    @Provides
    @ActivityScope
    VerifyJoinClanContract.ViewListener providesJoiningClanPresenter(ApiService apiService, JoinClanService joinClanService,
                                                                     UserService userService) {
        return new VerifyJoinClanPresenter(apiService,joinClanService, userService);
    }

    @Provides
    @ActivityScope
    JoinRequestsContract.ViewListener providesJoinRequestsPresenter(JoinClanService joinClanService) {
        return new JoinRequestsPresenter(joinClanService);
    }

    @Provides
    @ActivityScope
    HomeContract.ViewListener providesHomePresenter(ClanService clanService, UserService userService, ApiService apiService) {
        return new HomePresenter(clanService, userService, apiService);
    }

    @Provides
    @ActivityScope
    WarEnemyBasesContract.ViewListener providesWarPresenter(WarService warService, ClanService clanService) {
        return new WarEnemyBasesPresenter(warService, clanService);
    }

    @Provides
    @ActivityScope
    BasicWarInfoContract.ViewListener providesBasicWarInfoPresenter() {
        return new BasicWarInfoPresenter();
    }

    @Provides
    @ActivityScope
    WarOrderContract.ViewListener providesWarOrderPresenter(ApiService apiService) {
        return new WarOrderPresenter(apiService);
    }

    @Provides
    @ActivityScope
    WarBaseContract.ViewListener providesWarBasePresenter(WarService warService, ClanService clanService, UserService userService) {
        return new WarBasePresenter(warService, clanService, userService);
    }

    @Provides
    @ActivityScope
    WarViewPagerContract.ViewListener providesWarViewPagerPresenter(WarService warService) {
        return new WarViewPagerPresenter(warService);
    }
}
