package wickeddevs.easywars.ui.noclan.search;

import android.util.Log;

import java.util.ArrayList;

import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.data.service.contract.ApiService;

/**
 * Created by hicke_000 on 8/6/2016.
 */
public class SearchClansPresenter implements SearchClansContract.ViewListener {

    final static String TAG = "SearchClansPresenter";

    private SearchClansContract.View view;
    private ApiService apiService;

    private int startedBy;

    public SearchClansPresenter(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void registerView(SearchClansContract.View activity) {
        view = activity;
    }

    @Override
    public void onAttach() {
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void search(String query) {
        view.toggleProgressBar(true);
        apiService.searchClans(query, new ApiService.SearchApiClansCallback() {
            @Override
            public void onApiClansLoaded(ArrayList<ApiClan> apiClans) {
                view.toggleProgressBar(false);
                view.displaySearchResult(apiClans);
            }
        });
    }

    @Override
    public void selectedClan(ApiClan apiClan) {
        int something = view.getStartedBy();

        switch (view.getStartedBy()) {
            case SearchClansActivity.STARTED_FOR_CREATE:
                view.navigateToCreateClanUi(apiClan.tag);
                break;
            case SearchClansActivity.STARTED_FOR_JOIN:
                view.navigateToCreateClanUi(apiClan.tag);
                break;
            default:
                Log.e(TAG, "selectedClan: Started by value is invalid");
                break;
        }
    }

    @Override
    public void pressedBack() {

    }


}
