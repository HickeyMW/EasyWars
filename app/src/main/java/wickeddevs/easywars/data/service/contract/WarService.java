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

    interface LoadBaseCallback {
        void onLoaded(Base base);
    }

    void getLatestWar(LoadWarCallback callback);

    void startWar(War war);
    
    void loadBase(String warId, String baseId, LoadBaseCallback callback);

    void claimBase(String warId, String baseId);

    void removeClaim(String warId, String baseId);

    void addComment(String body, String warId, String baseId);
}
