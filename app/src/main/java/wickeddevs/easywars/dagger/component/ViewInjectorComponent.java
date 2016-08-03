package wickeddevs.easywars.dagger.component;

import dagger.Component;
import wickeddevs.easywars.dagger.module.PresenterModule;
import wickeddevs.easywars.dagger.module.StateServiceModule;
import wickeddevs.easywars.dagger.scope.ActivityScope;

import wickeddevs.easywars.ui.home.chat.ChatFragment;
import wickeddevs.easywars.ui.loadingsplash.LoadingSplashActivity;
import wickeddevs.easywars.ui.loadingsplash.LoadingSplashContract;
import wickeddevs.easywars.ui.noclan.NoClanActivity;

/**
 * Created by hicke_000 on 8/2/2016.
 */
@ActivityScope
@Component(dependencies = {ServiceComponent.class}, modules = PresenterModule.class)
public interface ViewInjectorComponent {

    void inject(LoadingSplashActivity loadingSplashActivity);

    void inject(ChatFragment chatFragment);

    void inject(NoClanActivity noClanActivity);
}
