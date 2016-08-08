package wickeddevs.easywars.dagger.module;

import dagger.Module;
import dagger.Provides;
import wickeddevs.easywars.dagger.scope.ActivityScope;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.ChatService;
import wickeddevs.easywars.data.service.contract.ClanService;
import wickeddevs.easywars.data.service.contract.CreateClanService;
import wickeddevs.easywars.data.service.contract.JoinClanService;
import wickeddevs.easywars.data.service.contract.StateService;
import wickeddevs.easywars.ui.home.chat.ChatContract;
import wickeddevs.easywars.ui.home.chat.ChatPresenter;
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
import wickeddevs.easywars.ui.noclan.search.SearchClansContract;
import wickeddevs.easywars.ui.noclan.search.SearchClansPresenter;

/**
 * Created by hicke_000 on 8/2/2016.
 */
@Module
public class PresenterModule {

    @Provides
    @ActivityScope
    LoadingSplashContract.ViewListener providesLoadingSplashPresenter(StateService stateService) {
        return new LoadingSplashPresenter(stateService);
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
                                                                     StateService stateService) {
        return new VerifyJoinClanPresenter(apiService,joinClanService, stateService);
    }
}
