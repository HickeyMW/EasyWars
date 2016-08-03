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
import wickeddevs.easywars.data.service.firebase.FbStateService;
import wickeddevs.easywars.ui.home.chat.ChatContract;
import wickeddevs.easywars.ui.home.chat.ChatPresenter;
import wickeddevs.easywars.ui.loadingsplash.LoadingSplashContract;
import wickeddevs.easywars.ui.loadingsplash.LoadingSplashPresenter;
import wickeddevs.easywars.ui.noclan.NoClanActivity;
import wickeddevs.easywars.ui.noclan.NoClanContract;
import wickeddevs.easywars.ui.noclan.NoClanPresenter;

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
    NoClanContract.ViewListener providesNoClanPresenter(StateService stateService, ApiService apiService,
                                                              CreateClanService createClanService,
                                                              JoinClanService joinClanService, ClanService clanService) {
        return new NoClanPresenter(stateService, apiService,createClanService, joinClanService, clanService);
    }
}
