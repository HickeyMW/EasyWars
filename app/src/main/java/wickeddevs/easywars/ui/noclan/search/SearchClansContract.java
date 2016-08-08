package wickeddevs.easywars.ui.noclan.search;

import java.util.ArrayList;

import wickeddevs.easywars.base.PView;
import wickeddevs.easywars.base.Presenter;
import wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by hicke_000 on 8/6/2016.
 */
public interface SearchClansContract {

    interface View extends PView {

        void displaySearchResult(ArrayList<ApiClan> apiClans);

        void navigateToCreateClanUi(String clanTag);

        void navigateToJoinClanUi(String clanTag);

        void clearDisplayedClans();

        void addClan(ApiClan apiClan);

        int getStartedBy();
    }

    interface ViewListener extends Presenter<SearchClansContract.View> {

        void search(String query);

        void selectedClan(ApiClan apiClan);

        void pressedBack();

    }
}
