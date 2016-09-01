package com.wickeddevs.easywars.dagger.component;

import dagger.Component;
import com.wickeddevs.easywars.dagger.module.PresenterModule;
import com.wickeddevs.easywars.dagger.scope.ActivityScope;

import com.wickeddevs.easywars.ui.home.HomeActivity;
import com.wickeddevs.easywars.ui.home.chat.ChatFragment;
import com.wickeddevs.easywars.ui.home.war.WarPlannerFragment;
import com.wickeddevs.easywars.ui.joinrequests.JoinRequestsActivity;
import com.wickeddevs.easywars.ui.loadingsplash.LoadingSplashActivity;
import com.wickeddevs.easywars.ui.noclan.create.CreateClanActivity;
import com.wickeddevs.easywars.ui.noclan.verifycreate.VerifyCreateClanActivity;
import com.wickeddevs.easywars.ui.noclan.join.JoinClanActivity;
import com.wickeddevs.easywars.ui.noclan.verifyjoin.VerifyJoinClanActivity;
import com.wickeddevs.easywars.ui.shared.search.SearchClansActivity;
import com.wickeddevs.easywars.ui.startwar.basicinfo.BasicWarInfoFragment;
import com.wickeddevs.easywars.ui.startwar.warorder.WarOrderFragment;
import com.wickeddevs.easywars.ui.warbase.WarBaseActivity;

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
