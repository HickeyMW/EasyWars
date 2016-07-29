package wickeddevs.easywars.noclan.create;

import java.util.ArrayList;

import wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public interface CreateClanContract {

    interface View {

        void showSearchResult(ArrayList<ApiClan> apiClans);

        void showDetailedClan(ApiClan apiClan);
    }

    interface ViewListener {

        void search(String query);

        void selectedClan(ApiClan apiClan);

        void selectedName(String name);

        void createClan();
    }
}
