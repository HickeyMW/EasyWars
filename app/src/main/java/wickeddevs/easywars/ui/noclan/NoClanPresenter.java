package wickeddevs.easywars.ui.noclan;

import android.util.Log;

import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.model.JoinDecision;
import wickeddevs.easywars.data.model.User;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.ClanService;
import wickeddevs.easywars.data.service.contract.CreateClanService;
import wickeddevs.easywars.data.service.contract.JoinClanService;
import wickeddevs.easywars.data.service.contract.StateService;

/**
 * Created by 375csptssce on 7/28/16.
 */
public class NoClanPresenter implements NoClanContract.ViewListener {

    final static String TAG = "LoadingSplashPresenter";

    private NoClanContract.View noClanView;
    private StateService stateService;
    private ApiService apiService;
    private CreateClanService createClanService;
    private JoinClanService joinClanService;
    private ClanService clanService;

    public NoClanPresenter(StateService stateService, ApiService apiService, CreateClanService createClanService,
                           JoinClanService joinClanService, ClanService clanService) {
        this.stateService = stateService;
        this.apiService = apiService;
        this.createClanService = createClanService;
        this.joinClanService = joinClanService;
        this.clanService = clanService;
    }


    @Override
    public void registerView(NoClanContract.View activity) {
        noClanView = activity;
    }

    @Override
    public void onAttach() {

        switch (stateService.getState()) {
            case StateService.STATE_BLANK:
                noClanView.showNoClanUi();
                break;
            case StateService.STATE_CREATING:
                noClanView.toggleProgressBar(true);
                loadForCreateRequest(stateService.getClanTag());
                break;
            case StateService.STATE_JOINING:
                loadForJoinRequest(stateService.getClanTag());
                break;
            default:
                Log.e(TAG,"Should not be able to go to default case");
        }
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void pressedCreateClan() {
        noClanView.showCreateClanUi();
    }

    @Override
    public void pressedJoinClan() {
        noClanView.showJoinClanUi();
    }

    @Override
    public void createClan(String username, String tag) {

    }

    @Override
    public void joinClan(String username, String tag, String message) {

    }

    @Override
    public void verifyCreateClan() {
        createClanService.verifyCreateRequest(new CreateClanService.VerifyCreateCallback() {
            @Override
            public void onVerificationLoaded(boolean isVerified) {
                if (isVerified) {
                    createClanService.deleteCreateRequest();
                    stateService.setState(User.ADMIN);
                    noClanView.showHomeUi();
                } else {
                    noClanView.displayCreateNotVerified();
                }
            }
        });
    }

    @Override
    public void cancelCreateClan() {
        createClanService.deleteCreateRequest();
        stateService.setState(User.BLANK);
        noClanView.showNoClanUi();
    }

    @Override
    public void cancelJoinClan() {
        joinClanService.deleteJoinRequest();
        stateService.setState(User.BLANK);
        noClanView.showNoClanUi();
    }

    private void loadForCreateRequest(final String clanTag) {
        createClanService.getCreateRequest(new CreateClanService.CreateRequestCallback() {
            @Override
            public void onCreateRequestLoaded(final CreateRequest createRequest) {
                apiService.getApiClan(clanTag, new ApiService.LoadApiClanCallback() {
                    @Override
                    public void onApiClanLoaded(ApiClan apiClan) {
                        noClanView.showAwaitingCreateUi(createRequest, apiClan);
                    }
                });
            }
        });
    }

    private void loadForJoinRequest(final String clanTag) {
        joinClanService.setDecisionListener(new JoinClanService.DecisionListener() {
            @Override
            public void onUpdate(JoinDecision joinDecision) {
                if (joinDecision.approved == JoinDecision.APPROVED) {
                    clanService.checkIfAdmin(new ClanService.AdminCheckCallback() {
                        @Override
                        public void onLoaded(boolean isAdmin) {
                            if (isAdmin) {
                                stateService.setState(User.ADMIN);
                            } else {
                                stateService.setState(User.MEMBER);
                            }
                            joinClanService.deleteJoinRequest();
                            noClanView.showHomeUi();
                        }
                    });
                } else if (joinDecision.approved == JoinDecision.DENIED) {
                    noClanView.displayJoinDenied();
                } else if (joinDecision.approved == JoinDecision.PENDING) {
                    apiService.getApiClan(clanTag, new ApiService.LoadApiClanCallback() {
                        @Override
                        public void onApiClanLoaded(ApiClan apiClan) {
                            noClanView.showAwaitingJoinUi(apiClan);
                        }
                    });
                }
            }
        });
    }
}
