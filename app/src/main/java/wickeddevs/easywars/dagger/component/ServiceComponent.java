package wickeddevs.easywars.dagger.component;

import dagger.Component;
import wickeddevs.easywars.dagger.module.ServiceModule;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.ChatService;
import wickeddevs.easywars.data.service.contract.ClanService;
import wickeddevs.easywars.data.service.contract.CreateClanService;
import wickeddevs.easywars.data.service.contract.JoinClanService;
import wickeddevs.easywars.data.service.contract.UserService;
import wickeddevs.easywars.data.service.contract.WarService;

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
