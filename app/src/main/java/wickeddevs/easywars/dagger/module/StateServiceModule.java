package wickeddevs.easywars.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import wickeddevs.easywars.dagger.scope.ActivityScope;
import wickeddevs.easywars.data.service.contract.StateService;
import wickeddevs.easywars.data.service.firebase.FbStateService;

/**
 * Created by hicke_000 on 8/2/2016.
 */
@Module
public class StateServiceModule {

    private static FbStateService fbStateService;

    public StateServiceModule() {
        fbStateService = new FbStateService();
    }

    @Provides
    StateService providesStateService() {
        return fbStateService;
    }
}
