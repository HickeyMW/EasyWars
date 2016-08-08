package wickeddevs.easywars.ui.noclan.join;

import java.util.ArrayList;

import wickeddevs.easywars.base.PView;
import wickeddevs.easywars.base.Presenter;
import wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by hicke_000 on 8/3/2016.
 */
public interface JoinClanContract {

    interface View extends PView {

        void displaySearchResult(ArrayList<ApiClan> apiClans);

        void displayDetailedClan(ApiClan apiClan);

        void navigateToNoClanUi();

        void navigateToJoiningClanUi();
    }

    interface ViewListener extends Presenter<JoinClanContract.View> {

        void search(String query);

        void selectedClan(ApiClan apiClan);

        void selectedName(String name);

        void createClan();
    }
}
