package wickeddevs.easywars.dagger.module;

import dagger.Module;
import dagger.Provides;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.ChatService;
import wickeddevs.easywars.data.service.contract.ClanService;
import wickeddevs.easywars.data.service.contract.CreateClanService;
import wickeddevs.easywars.data.service.contract.JoinClanService;
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
    ChatService providesChatService() {
        return new FbChatService();
    }

//    @Provides
//    StateService providesStateService() {
//        return stateService;
//    }

    @Provides
    ClanService providesClanService() {
        return new FbClanService();
    }

    @Provides
    CreateClanService providesCreateClanService() {
        return new FbCreateClanService();
    }

    @Provides
    JoinClanService providesJoinClanService() {
        return new FbJoinClanService();
    }


}
