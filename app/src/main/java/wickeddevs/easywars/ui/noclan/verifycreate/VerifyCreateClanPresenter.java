package wickeddevs.easywars.ui.noclan.verifycreate;

import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.CreateClanService;

/**
 * Created by hicke_000 on 8/3/2016.
 */
public class VerifyCreateClanPresenter implements VerifyCreateClanContract.ViewListener {

    private VerifyCreateClanContract.View creatingClanView;
    private ApiService apiService;
    private CreateClanService createClanService;

    public VerifyCreateClanPresenter(ApiService apiService, CreateClanService createClanService) {
        this.apiService = apiService;
        this.createClanService = createClanService;
    }


    @Override
    public void registerView(VerifyCreateClanContract.View activity) {
        creatingClanView = activity;
    }

    @Override
    public void onAttach() {
        createClanService.getCreateRequest(new CreateClanService.CreateRequestCallback() {
            @Override
            public void onCreateRequestLoaded(final CreateRequest createRequest) {
                apiService.getApiClan(createRequest.tag, new ApiService.LoadApiClanCallback() {
                    @Override
                    public void onApiClanLoaded(ApiClan apiClan) {
                        creatingClanView.displayCreateRequestDetails(createRequest, apiClan);
                    }
                });
            }
        });
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void verifyCreateClan() {
        createClanService.verifyCreateRequest(new CreateClanService.VerifyCreateCallback() {
            @Override
            public void onVerificationLoaded(boolean isVerified) {
                if (isVerified) {
                    createClanService.deleteCreateRequest();
                    creatingClanView.navigateToHomeUi();
                } else {
                    creatingClanView.displayCreateNotVerified();
                }
            }
        });
    }

    @Override
    public void cancelCreateClan() {
        createClanService.deleteCreateRequest();
        creatingClanView.navigateToNoClanUi();
    }
}
