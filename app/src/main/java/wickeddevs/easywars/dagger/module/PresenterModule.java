package wickeddevs.easywars.dagger.module;

import dagger.Module;
import dagger.Provides;
import wickeddevs.easywars.dagger.scope.ActivityScope;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.ChatService;
import wickeddevs.easywars.data.service.contract.ClanService;
import wickeddevs.easywars.data.service.contract.CreateClanService;
import wickeddevs.easywars.data.service.contract.JoinClanService;
import wickeddevs.easywars.data.service.contract.UserService;
import wickeddevs.easywars.data.service.contract.WarService;
import wickeddevs.easywars.ui.home.HomeContract;
import wickeddevs.easywars.ui.home.HomePresenter;
import wickeddevs.easywars.ui.home.chat.ChatContract;
import wickeddevs.easywars.ui.home.chat.ChatPresenter;
import wickeddevs.easywars.ui.home.war.WarPlannerContract;
import wickeddevs.easywars.ui.home.war.WarPlannerPresenter;
import wickeddevs.easywars.ui.joinrequests.JoinRequestsContract;
import wickeddevs.easywars.ui.joinrequests.JoinRequestsPresenter;
import wickeddevs.easywars.ui.loadingsplash.LoadingSplashContract;
import wickeddevs.easywars.ui.loadingsplash.LoadingSplashPresenter;
import wickeddevs.easywars.ui.noclan.create.CreateClanContract;
import wickeddevs.easywars.ui.noclan.create.CreateClanPresenter;
import wickeddevs.easywars.ui.noclan.verifycreate.VerifyCreateClanContract;
import wickeddevs.easywars.ui.noclan.verifycreate.VerifyCreateClanPresenter;
import wickeddevs.easywars.ui.noclan.join.JoinClanContract;
import wickeddevs.easywars.ui.noclan.join.JoinClanPresenter;
import wickeddevs.easywars.ui.noclan.verifyjoin.VerifyJoinClanContract;
import wickeddevs.easywars.ui.noclan.verifyjoin.VerifyJoinClanPresenter;
import wickeddevs.easywars.ui.shared.search.SearchClansContract;
import wickeddevs.easywars.ui.shared.search.SearchClansPresenter;
import wickeddevs.easywars.ui.startwar.basicinfo.BasicWarInfoContract;
import wickeddevs.easywars.ui.startwar.basicinfo.BasicWarInfoPresenter;
import wickeddevs.easywars.ui.startwar.warorder.WarOrderContract;
import wickeddevs.easywars.ui.startwar.warorder.WarOrderPresenter;
import wickeddevs.easywars.ui.warbase.WarBaseContract;
import wickeddevs.easywars.ui.warbase.WarBasePresenter;

/**
 * Created by hicke_000 on 8/2/2016.
 */
@Module
public class PresenterModule {

    @Provides
    @ActivityScope
    LoadingSplashContract.ViewListener providesLoadingSplashPresenter(UserService userService) {
        return new LoadingSplashPresenter(userService);
    }

    @Provides
    @ActivityScope
    ChatContract.ViewListener providesChatContractPresenter(ChatService chatService, ClanService clanService) {
        return new ChatPresenter(chatService, clanService);
    }

    @Provides
    @ActivityScope
    SearchClansContract.ViewListener providesSearchClansPresenter(ApiService apiService) {
        return new SearchClansPresenter(apiService);
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
    WarPlannerContract.ViewListener providesWarPresenter(WarService warService, ClanService clanService) {
        return new WarPlannerPresenter(warService, clanService);
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
    WarBaseContract.ViewListener providesWarBasePresenter(WarService warService) {
        return new WarBasePresenter(warService);
    }
}
