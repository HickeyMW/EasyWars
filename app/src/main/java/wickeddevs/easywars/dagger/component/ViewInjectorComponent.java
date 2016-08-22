package wickeddevs.easywars.dagger.component;

import dagger.Component;
import wickeddevs.easywars.dagger.module.PresenterModule;
import wickeddevs.easywars.dagger.scope.ActivityScope;

import wickeddevs.easywars.ui.home.HomeActivity;
import wickeddevs.easywars.ui.home.chat.ChatFragment;
import wickeddevs.easywars.ui.home.war.WarPlannerFragment;
import wickeddevs.easywars.ui.joinrequests.JoinRequestsActivity;
import wickeddevs.easywars.ui.loadingsplash.LoadingSplashActivity;
import wickeddevs.easywars.ui.noclan.create.CreateClanActivity;
import wickeddevs.easywars.ui.noclan.verifycreate.VerifyCreateClanActivity;
import wickeddevs.easywars.ui.noclan.join.JoinClanActivity;
import wickeddevs.easywars.ui.noclan.verifyjoin.VerifyJoinClanActivity;
import wickeddevs.easywars.ui.shared.search.SearchClansActivity;
import wickeddevs.easywars.ui.startwar.basicinfo.BasicWarInfoFragment;
import wickeddevs.easywars.ui.startwar.warorder.WarOrderFragment;
import wickeddevs.easywars.ui.warbase.WarBaseActivity;

/**
 * Created by hicke_000 on 8/2/2016.
 */
@ActivityScope
@Component(dependencies = {ServiceComponent.class}, modules = PresenterModule.class)
public interface ViewInjectorComponent {

    void inject(LoadingSplashActivity loadingSplashActivity);

    void inject(ChatFragment chatFragment);

    void inject(SearchClansActivity searchClansActivity);

    void inject(CreateClanActivity createClanActivity);

    void inject(JoinClanActivity joinClanActivity);

    void inject(VerifyCreateClanActivity verifyCreateClanActivity);

    void inject(VerifyJoinClanActivity verifyJoinClanActivity);

    void inject(JoinRequestsActivity joinRequestsActivity);

    void inject(HomeActivity homeActivity);

    void inject(WarPlannerFragment warPlannerFragment);

    void inject(BasicWarInfoFragment basicWarInfoFragment);

    void inject(WarOrderFragment warOrderFragment);

    void inject(WarBaseActivity warBaseActivity);
}
