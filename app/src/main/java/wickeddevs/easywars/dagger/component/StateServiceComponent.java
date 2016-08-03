package wickeddevs.easywars.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import wickeddevs.easywars.dagger.module.PresenterModule;
import wickeddevs.easywars.dagger.module.StateServiceModule;
import wickeddevs.easywars.dagger.scope.ActivityScope;
import wickeddevs.easywars.data.service.contract.StateService;

/**
 * Created by hicke_000 on 8/2/2016.
 */
@Component(modules = StateServiceModule.class)
public interface StateServiceComponent {

    StateService providesStateService();
}
