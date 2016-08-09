package wickeddevs.easywars.ui.noclan.verifycreate;

import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.CreateClanService;

/**
 * Created by hicke_000 on 8/3/2016.
 */
public class VerifyCreateClanPresenter implements VerifyCreateClanContract.ViewListener {

    private VerifyCreateClanContract.View view;
    private ApiService apiService;
    private CreateClanService createClanService;

    long timeOfLastVerification = 0;

    public VerifyCreateClanPresenter(ApiService apiService, CreateClanService createClanService) {
        this.apiService = apiService;
        this.createClanService = createClanService;
    }


    @Override
    public void registerView(VerifyCreateClanContract.View activity) {
        view = activity;
    }

    @Override
    public void onAttach() {
        createClanService.getCreateRequest(new CreateClanService.CreateRequestCallback() {
            @Override
            public void onCreateRequestLoaded(final CreateRequest createRequest) {
                apiService.getApiClan(createRequest.tag, new ApiService.LoadApiClanCallback() {
                    @Override
                    public void onApiClanLoaded(ApiClan apiClan) {
                        view.displayCreateRequestDetails(createRequest, apiClan);
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
        if (System.currentTimeMillis() > timeOfLastVerification + 5000) {
            createClanService.verifyCreateRequest(new CreateClanService.VerifyCreateCallback() {
                @Override
                public void onVerificationLoaded(boolean isVerified) {
                    if (isVerified) {
                        createClanService.deleteCreateRequest();
                        view.navigateToHomeUi();
                    } else {
                        view.displayMessage("The code was not found in the clan description");
                        timeOfLastVerification = System.currentTimeMillis();
                    }
                }
            });
        } else {
            long milisRemaining = (timeOfLastVerification + 5000) - System.currentTimeMillis();
            double secondsRemaining = (double) Math.round((milisRemaining) / 100) / 10;
            view.displayMessage("Please wait " + secondsRemaining + " more seconds before trying again");
        }

    }

    @Override
    public void cancelCreateClan() {
        createClanService.deleteCreateRequest();
        view.navigateToNoClanUi();
    }
}
