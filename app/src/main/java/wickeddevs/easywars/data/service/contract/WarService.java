package wickeddevs.easywars.data.service.contract;

import java.util.ArrayList;

import wickeddevs.easywars.data.model.war.Base;
import wickeddevs.easywars.data.model.war.War;

/**
 * Created by 375csptssce on 8/16/16.
 */
public interface WarService {

    interface LoadWarCallback {
        void onLoaded(War war);
    }

    void getLatestWar(LoadWarCallback callback);
}
