package wickeddevs.easywars.dagger.component;

import dagger.Component;
import wickeddevs.easywars.dagger.module.PresenterModule;
import wickeddevs.easywars.dagger.module.ServiceModule;
import wickeddevs.easywars.dagger.scope.ActivityScope;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.ChatService;
import wickeddevs.easywars.data.service.contract.ClanService;
import wickeddevs.easywars.data.service.contract.CreateClanService;
import wickeddevs.easywars.data.service.contract.JoinClanService;
import wickeddevs.easywars.data.service.contract.StateService;

/**
 * Created by hicke_000 on 8/2/2016.
 */
@Component(dependencies = {StateServiceComponent.class}, modules = ServiceModule.class)
public interface ServiceComponent {

    ApiService providesApiService();

    ChatService providesChatService();

    StateService providesStateService();

    ClanService providesClanService();

    CreateClanService providesCreateClanService();

    JoinClanService providesJoinClanService();
}
