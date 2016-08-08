package wickeddevs.easywars.ui.noclan.create;

import android.util.Log;

import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.CreateClanService;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class CreateClanPresenter implements CreateClanContract.ViewListener {

    final static String TAG = "CreateClanPresenter";

    private ApiClan apiClan;
    private String name;

    private CreateClanContract.View view;
    private ApiService apiService;
    private CreateClanService createClanService;

    public CreateClanPresenter(ApiService apiService, CreateClanService createClanService) {
        this.apiService = apiService;
        this.createClanService = createClanService;
    }

    @Override
    public void registerView(CreateClanContract.View activity) {
        view = activity;
    }

    @Override
    public void onAttach() {
        String clanTag = view.getClanTag();
        if (clanTag != null) {
            view.toggleProgressBar(true);
            apiService.getApiClan(view.getClanTag(), new ApiService.LoadApiClanCallback() {
                @Override
                public void onApiClanLoaded(ApiClan apiClan) {
                    view.toggleProgressBar(false);
                    CreateClanPresenter.this.apiClan = apiClan;
                    view.displayClanInfo(apiClan);
                }
            });
        } else {
            Log.e(TAG, "onAttach: Clan tag was null");
        }

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void selectedName(String name) {
        view.allowCreate();
        this.name = name;
    }

    @Override
    public void createClanRequest() {
        createClanService.setCreateRequest(name, apiClan.tag);
        view.navigateToVerifyCreateClanUi();
    }


}
