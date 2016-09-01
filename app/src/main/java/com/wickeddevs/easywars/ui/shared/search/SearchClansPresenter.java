package com.wickeddevs.easywars.ui.shared.search;

import android.util.Log;

import java.util.ArrayList;

import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.data.service.contract.ApiService;
import com.wickeddevs.easywars.data.service.contract.JoinClanService;

/**
 * Created by hicke_000 on 8/6/2016.
 */
public class SearchClansPresenter implements SearchClansContract.ViewListener {

    final static String TAG = "SearchClansPresenter";

    private SearchClansContract.View view;
    private ApiService apiService;
    private JoinClanService joinClanService;

    public SearchClansPresenter(ApiService apiService, JoinClanService joinClanService) {
        this.apiService = apiService;
        this.joinClanService = joinClanService;
    }

    @Override
    public void registerView(SearchClansContract.View activity) {
        view = activity;
    }


    @Override
    public void search(String query) {
        query = query.toLowerCase();
        if (query.length() < 3) {
            view.displayQueryTooShort();
        } else if (view.getStartedBy() == SearchClansActivity.STARTED_FOR_JOIN) {
            view.clearClans();
            view.toggleLoading(true);
            joinClanService.searchJoinableClans(query, new JoinClanService.ClanTagsCallback() {
                @Override
                public void onLoaded(ArrayList<String> clanTags) {
                    if (clanTags.size() > 0) {
                        view.toggleLoading(false);
                        for (String clanTag : clanTags) {
                            apiService.getApiClan(clanTag, new ApiService.LoadApiClanCallback() {
                                @Override
                                public void onApiClanLoaded(ApiClan apiClan) {
                                    view.addClan(apiClan);
                                }
                            });
                        }
                    } else {
                        view.toggleLoading(false);
                        view.displayMessage("No results found");
                    }

                }
            });
        }
        else {
            view.toggleLoading(true);
            apiService.searchClans(query, new ApiService.SearchApiClansCallback() {
                @Override
                public void onApiClansLoaded(ArrayList<ApiClan> apiClans) {
                    view.toggleLoading(false);
                    view.displaySearchResult(apiClans);
                }
            });
        }
    }

    @Override
    public void selectedClan(ApiClan apiClan) {
        int something = view.getStartedBy();
        //FbInfo.setClanTag(apiClan.tag);
        switch (view.getStartedBy()) {
            case SearchClansActivity.STARTED_FOR_CREATE:
                view.navigateToCreateClanUi(apiClan.tag);
                break;
            case SearchClansActivity.STARTED_FOR_JOIN:
                view.navigateToJoinClanUi(apiClan.tag);
                break;
            case SearchClansActivity.STARTED_FOR_WAR:
                view.navigateToWarUi(apiClan);
                break;
            default:
                Log.e(TAG, "selectedClan: Started by value is invalid");
                break;
        }
    }
}
