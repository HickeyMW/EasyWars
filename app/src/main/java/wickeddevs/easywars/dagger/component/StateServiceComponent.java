package wickeddevs.easywars.dagger.component;

import dagger.Component;
import wickeddevs.easywars.dagger.module.StateServiceModule;
import wickeddevs.easywars.data.service.contract.UserService;

/**
 * Created by hicke_000 on 8/2/2016.
 */
@Component(modules = StateServiceModule.class)
public interface StateServiceComponent {

    UserService providesStateService();
}
