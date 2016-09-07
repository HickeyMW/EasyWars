package com.wickeddevs.easywars.dagger;

import com.wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import com.wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.easywars.dagger.component.ServiceComponent;
import com.wickeddevs.easywars.dagger.component.ViewInjectorComponent;
import com.wickeddevs.easywars.ui.home.HomeActivity;
import com.wickeddevs.easywars.ui.home.chat.ChatFragment;
import com.wickeddevs.easywars.ui.home.war.WarViewPagerFragment;
import com.wickeddevs.easywars.ui.home.war.enemybases.WarEnemyBasesFragment;
import com.wickeddevs.easywars.ui.joinrequests.JoinRequestsActivity;
import com.wickeddevs.easywars.ui.loadingsplash.LoadingSplashActivity;
import com.wickeddevs.easywars.ui.noclan.create.CreateClanActivity;
import com.wickeddevs.easywars.ui.noclan.verifycreate.VerifyCreateClanActivity;
import com.wickeddevs.easywars.ui.noclan.join.JoinClanActivity;
import com.wickeddevs.easywars.ui.noclan.verifyjoin.VerifyJoinClanActivity;
import com.wickeddevs.easywars.ui.shared.search.SearchClansActivity;
import com.wickeddevs.easywars.ui.startwar.info.WarInfoActivity;
import com.wickeddevs.easywars.ui.startwar.warorder.WarOrderActivity;
import com.wickeddevs.easywars.ui.warbase.WarBaseActivity;

/**
 * Created by hicke_000 on 8/2/2016.
 */
public enum Injector {
    INSTANCE;

    private Injector(){
    }

    public void inject(LoadingSplashActivity loadingSplashActivity) {
        getViewInjectorComponent().inject(loadingSplashActivity);
    }

    public void inject(ChatFragment chatFragment) {
        getViewInjectorComponent().inject(chatFragment);
    }

    public void inject(SearchClansActivity searchClansActivity) {
        getViewInjectorComponent().inject(searchClansActivity);
    }

    public void inject(CreateClanActivity createClanActivity) {
        getViewInjectorComponent().inject(createClanActivity);
    }

    public void inject(JoinClanActivity joinClanActivity) {
        getViewInjectorComponent().inject(joinClanActivity);
    }

    public void inject(VerifyCreateClanActivity verifyCreateClanActivity) {
        getViewInjectorComponent().inject(verifyCreateClanActivity);
    }

    public void inject(VerifyJoinClanActivity verifyJoinClanActivity) {
        getViewInjectorComponent().inject(verifyJoinClanActivity);
    }

    public void inject(JoinRequestsActivity joinRequestsActivity) {
        getViewInjectorComponent().inject(joinRequestsActivity);
    }

    public void inject(HomeActivity homeActivity) {
        getViewInjectorComponent().inject(homeActivity);
    }

    public void inject(WarEnemyBasesFragment warEnemyBasesFragment) {
        getViewInjectorComponent().inject(warEnemyBasesFragment);
    }

    public void inject(WarBaseActivity warBaseActivity) {
        getViewInjectorComponent().inject(warBaseActivity);
    }

    public void inject(WarViewPagerFragment warViewPagerFragment) {
        getViewInjectorComponent().inject(warViewPagerFragment);
    }

    public void inject(WarInfoActivity warInfoActivity) {
        getViewInjectorComponent().inject(warInfoActivity);
    }

    public void inject(WarOrderActivity warOrderActivity) {
        getViewInjectorComponent().inject(warOrderActivity);
    }



    private ViewInjectorComponent getViewInjectorComponent() {
        return DaggerViewInjectorComponent.builder()
                    .serviceComponent(getServiceComponent()).build();

    }

    private ServiceComponent getServiceComponent() {
        return DaggerServiceComponent.builder().build();
    }
}
