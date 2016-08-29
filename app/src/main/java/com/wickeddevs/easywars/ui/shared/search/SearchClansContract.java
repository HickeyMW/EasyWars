package com.wickeddevs.easywars.ui.shared.search;

import java.util.ArrayList;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by hicke_000 on 8/6/2016.
 */
public interface SearchClansContract {

    interface View extends PView {

        void setScreenTitle(String title);

        void displaySearchResult(ArrayList<ApiClan> apiClans);

        void displayQueryTooShort();

        void navigateToCreateClanUi(String clanTag);

        void navigateToJoinClanUi(String clanTag);

        void navigateToWarUi(ApiClan apiClan);

        void clearDisplayedClans();

        void addClan(ApiClan apiClan);

        int getStartedBy();

        void toggleLoading(boolean loading);
    }

    interface ViewListener extends Presenter<SearchClansContract.View> {

        void search(String query);

        void selectedClan(ApiClan apiClan);
    }
}
