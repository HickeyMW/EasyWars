package com.wickeddevs.easywars.dagger.module;

import dagger.Module;
import dagger.Provides;
import com.wickeddevs.easywars.data.service.contract.ApiService;
import com.wickeddevs.easywars.data.service.contract.ChatService;
import com.wickeddevs.easywars.data.service.contract.ClanService;
import com.wickeddevs.easywars.data.service.contract.CreateClanService;
import com.wickeddevs.easywars.data.service.contract.JoinClanService;
import com.wickeddevs.easywars.data.service.contract.UserService;
import com.wickeddevs.easywars.data.service.contract.VersionService;
import com.wickeddevs.easywars.data.service.contract.WarService;
import com.wickeddevs.easywars.data.service.firebase.FbApiService;
import com.wickeddevs.easywars.data.service.firebase.FbChatService;
import com.wickeddevs.easywars.data.service.firebase.FbClanService;
import com.wickeddevs.easywars.data.service.firebase.FbCreateClanService;
import com.wickeddevs.easywars.data.service.firebase.FbJoinClanService;
import com.wickeddevs.easywars.data.service.firebase.FbUserService;
import com.wickeddevs.easywars.data.service.firebase.FbVersionService;
import com.wickeddevs.easywars.data.service.firebase.FbWarService;

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

    @Provides
    UserService providesStateService() {
        return new FbUserService();
    }

    @Provides
    WarService providesWarService() {
        return new FbWarService();
    }

    @Provides
    VersionService providesVersionService() {
        return new FbVersionService();
    }


}
