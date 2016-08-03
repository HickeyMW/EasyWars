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
import wickeddevs.easywars.data.service.firebase.FbApiService;
import wickeddevs.easywars.data.service.firebase.FbChatService;
import wickeddevs.easywars.data.service.firebase.FbClanService;
import wickeddevs.easywars.data.service.firebase.FbCreateClanService;
import wickeddevs.easywars.data.service.firebase.FbJoinClanService;

/**
 * Created by hicke_000 on 8/2/2016.
 */
@Module
public class ServiceModule {

    @Provides
    ApiService providesApiService() {
        return new FbApiService();
    }

    @Provides
    ChatService providesChatService(StateService stateService) {
        return new FbChatService(stateService);
    }

//    @Provides
//    StateService providesStateService(StateService stateService) {
//        return stateService;
//    }

    @Provides
    ClanService providesClanService(StateService stateService) {
        return new FbClanService(stateService);
    }

    @Provides
    CreateClanService providesCreateClanService(StateService stateService) {
        return new FbCreateClanService(stateService);
    }

    @Provides
    JoinClanService providesJoinClanService(StateService stateService) {
        return new FbJoinClanService(stateService);
    }


}
