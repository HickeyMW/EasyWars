package wickeddevs.easywars.noclan.create;

import java.util.ArrayList;

import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.CreateClanService;
import wickeddevs.easywars.data.service.contract.UserService;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class CreateClanPresenter implements CreateClanContract.ViewListener {

    private ApiClan mApiClan;
    private String mName;
    private CreateClanContract.View mCreateClanView;
    private ApiService mApiService;
    private CreateClanService mCreateClanService;
    private UserService mUserService;

    public CreateClanPresenter(CreateClanContract.View createClanView, ApiService apiService, CreateClanService createClanService, UserService userService) {
        mCreateClanView = createClanView;
        mApiService = apiService;
        mCreateClanService = createClanService;
        mUserService = userService;
    }

    @Override
    public void selectedClan(ApiClan apiClan) {
        mApiClan = apiClan;
        mApiService.getApiClan(apiClan.tag, new ApiService.LoadApiClanCallback() {
            @Override
            public void onApiClanLoaded(ApiClan apiClan) {
                mCreateClanView.showDetailedClan(apiClan);
            }
        });
    }

    @Override
    public void search(String query) {
        mApiService.searchClans(query, new ApiService.SearchApiClansCallback() {
            @Override
            public void onApiClansLoaded(ArrayList<ApiClan> apiClans) {
                mCreateClanView.showDetailedClan(apiClans);
            }
        });
    }

    @Override
    public void selectedName(String name) {
        mName = name;
    }

    @Override
    public void createClan() {
        CreateRequest createRequest = new CreateRequest(mName, )
        mCreateClanService.setCreateRequest();
    }
}
