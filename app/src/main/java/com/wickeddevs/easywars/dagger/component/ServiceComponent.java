package com.wickeddevs.easywars.dagger.component;

import dagger.Component;
import com.wickeddevs.easywars.dagger.module.ServiceModule;
import com.wickeddevs.easywars.data.service.contract.ApiService;
import com.wickeddevs.easywars.data.service.contract.ChatService;
import com.wickeddevs.easywars.data.service.contract.ClanService;
import com.wickeddevs.easywars.data.service.contract.CreateClanService;
import com.wickeddevs.easywars.data.service.contract.JoinClanService;
import com.wickeddevs.easywars.data.service.contract.UserService;
import com.wickeddevs.easywars.data.service.contract.WarService;

/**
 * Created by hicke_000 on 8/2/2016.
 */
@Component(modules = ServiceModule.class)
public interface ServiceComponent {

    ApiService providesApiService();

    ChatService providesChatService();

    UserService providesStateService();

    ClanService providesClanService();

    CreateClanService providesCreateClanService();

    JoinClanService providesJoinClanService();

    WarService providesWarService();
}
