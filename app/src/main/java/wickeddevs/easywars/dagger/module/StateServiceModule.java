package wickeddevs.easywars.dagger.module;

import dagger.Module;
import dagger.Provides;
import wickeddevs.easywars.data.service.contract.StateService;
import wickeddevs.easywars.data.service.firebase.FbInfo;

/**
 * Created by hicke_000 on 8/2/2016.
 */
@Module
public class StateServiceModule {

    @Provides
    StateService providesStateService() {
        return FbInfo.INSTANCE;
    }
}
