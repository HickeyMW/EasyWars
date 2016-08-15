package wickeddevs.easywars.dagger;

import wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import wickeddevs.easywars.dagger.component.DaggerStateServiceComponent;
import wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import wickeddevs.easywars.dagger.component.ServiceComponent;
import wickeddevs.easywars.dagger.component.StateServiceComponent;
import wickeddevs.easywars.dagger.component.ViewInjectorComponent;
import wickeddevs.easywars.ui.home.chat.ChatFragment;
import wickeddevs.easywars.ui.joinrequests.JoinRequestsActivity;
import wickeddevs.easywars.ui.loadingsplash.LoadingSplashActivity;
import wickeddevs.easywars.ui.noclan.NoClanActivity;
import wickeddevs.easywars.ui.noclan.create.CreateClanActivity;
import wickeddevs.easywars.ui.noclan.verifycreate.VerifyCreateClanActivity;
import wickeddevs.easywars.ui.noclan.join.JoinClanActivity;
import wickeddevs.easywars.ui.noclan.verifyjoin.VerifyJoinClanActivity;
import wickeddevs.easywars.ui.noclan.search.SearchClansActivity;

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



    private ViewInjectorComponent getViewInjectorComponent() {
        return DaggerViewInjectorComponent.builder()
                    .serviceComponent(getServiceComponent()).build();
    }

    private ServiceComponent getServiceComponent() {
        return DaggerServiceComponent.builder()
                    .stateServiceComponent(getStateServiceComponent()).build();
    }

    private StateServiceComponent getStateServiceComponent() {
        return DaggerStateServiceComponent.builder().build();
    }
}
