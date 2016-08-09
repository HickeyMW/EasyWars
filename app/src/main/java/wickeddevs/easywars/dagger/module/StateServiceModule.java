package wickeddevs.easywars.dagger.module;

import dagger.Module;
import dagger.Provides;
import wickeddevs.easywars.data.service.contract.UserService;
import wickeddevs.easywars.data.service.firebase.FbInfo;
import wickeddevs.easywars.data.service.firebase.FbUserService;

/**
 * Created by hicke_000 on 8/2/2016.
 */
@Module
public class StateServiceModule {

    @Provides
    UserService providesStateService() {
        return new FbUserService();
    }
}
