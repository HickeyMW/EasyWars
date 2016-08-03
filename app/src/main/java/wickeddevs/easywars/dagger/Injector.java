package wickeddevs.easywars.dagger;

import wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import wickeddevs.easywars.dagger.component.DaggerStateServiceComponent;
import wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import wickeddevs.easywars.dagger.component.ServiceComponent;
import wickeddevs.easywars.dagger.component.StateServiceComponent;
import wickeddevs.easywars.dagger.component.ViewInjectorComponent;
import wickeddevs.easywars.ui.home.chat.ChatFragment;
import wickeddevs.easywars.ui.loadingsplash.LoadingSplashActivity;
import wickeddevs.easywars.ui.loadingsplash.LoadingSplashContract;
import wickeddevs.easywars.ui.noclan.NoClanActivity;

/**
 * Created by hicke_000 on 8/2/2016.
 */
public enum Injector {
    INSTANCE;

    private StateServiceComponent stateServiceComponent = null;
    private ViewInjectorComponent viewInjectorComponent = null;
    private ServiceComponent serviceComponent = null;

    private Injector(){
    }

    public void inject(LoadingSplashActivity loadingSplashActivity) {
        getViewInjectorComponent().inject(loadingSplashActivity);
    }

    public void inject(ChatFragment chatFragment) {
        getViewInjectorComponent().inject(chatFragment);
    }

    public void inject(NoClanActivity noClanActivity) {
        getViewInjectorComponent().inject(noClanActivity);
    }

    private ViewInjectorComponent getViewInjectorComponent() {
        if (viewInjectorComponent == null) {
            viewInjectorComponent = DaggerViewInjectorComponent.builder()
                    .serviceComponent(getServiceComponent()).build();
        }
        return viewInjectorComponent;
    }

    private ServiceComponent getServiceComponent() {
        if (serviceComponent ==  null) {
            serviceComponent = DaggerServiceComponent.builder()
                    .stateServiceComponent(getStateServiceComponent()).build();
        }
        return serviceComponent;
    }

    private StateServiceComponent getStateServiceComponent() {
        if (stateServiceComponent ==  null) {
            stateServiceComponent = DaggerStateServiceComponent.builder().build();
        }
        return stateServiceComponent;
    }
}
