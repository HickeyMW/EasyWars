package wickeddevs.easywars.ui.noclan.create;

import java.util.ArrayList;

import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.CreateClanService;
import wickeddevs.easywars.data.service.contract.StateService;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class CreateClanPresenter implements CreateClanContract.ViewListener {

    private ApiClan apiClan;
    private String name;
    private CreateClanContract.View createClanView;
    private ApiService apiService;
    private CreateClanService createClanService;
    private StateService stateService;

    public CreateClanPresenter(ApiClan apiClan, String name, CreateClanContract.View createClanView,
                               ApiService apiService, CreateClanService createClanService,
                               StateService stateService) {
        this.apiClan = apiClan;
        this.name = name;
        this.createClanView = createClanView;
        this.apiService = apiService;
        this.createClanService = createClanService;
        this.stateService = stateService;
    }


    @Override
    public void selectedClan(ApiClan apiClan) {
        this.apiClan = apiClan;
        apiService.getApiClan(apiClan.tag, new ApiService.LoadApiClanCallback() {
            @Override
            public void onApiClanLoaded(ApiClan apiClan) {
                createClanView.showDetailedClan(apiClan);
            }
        });
    }

    @Override
    public void search(String query) {
        apiService.searchClans(query, new ApiService.SearchApiClansCallback() {
            @Override
            public void onApiClansLoaded(ArrayList<ApiClan> apiClans) {
                //createClanView.showDetailedClan(apiClans);
            }
        });
    }

    @Override
    public void selectedName(String name) {
        this.name = name;
    }

    @Override
    public void createClan() {
        //CreateRequest createRequest = new CreateRequest(name, )
        //createClanService.setCreateRequest();
    }
}
