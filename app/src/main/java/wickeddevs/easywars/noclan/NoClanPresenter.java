package wickeddevs.easywars.noclan;

import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.model.JoinDecision;
import wickeddevs.easywars.data.model.User;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.ClanService;
import wickeddevs.easywars.data.service.contract.CreateClanService;
import wickeddevs.easywars.data.service.contract.JoinClanService;
import wickeddevs.easywars.data.service.contract.UserService;

/**
 * Created by 375csptssce on 7/28/16.
 */
public class NoClanPresenter implements NoClanContract.ViewListener {

    final static String TAG = "LoadingSplashPresenter";
    private final NoClanContract.View mNoClanView;
    private final UserService mUserService;
    private final ApiService mApiService;
    private final CreateClanService mCreateClanService;
    private final JoinClanService mJoinClanService;
    private final ClanService mClanService;

    public NoClanPresenter(NoClanContract.View noClanView, UserService userService,
                           ApiService apiService, CreateClanService createClanService,
                           JoinClanService joinClanService, ClanService clanService) {

        mNoClanView = noClanView;
        mUserService = userService;
        mApiService = apiService;
        mCreateClanService = createClanService;
        mJoinClanService = joinClanService;
        mClanService = clanService;
    }

    @Override
    public void onStart() {
        mUserService.getUser(new UserService.LoadUserCallback() {
            @Override
            public void onUserLoaded(User user) {
                if (user.state == User.BLANK) {
                    mNoClanView.showNoClanUi();
                } else if (user.state == User.CREATING) {
                    loadForCreateRequest(user.clanTag);

                } else if (user.state == User.JOINING) {
                    loadForJoinRequest(user.clanTag);
                }
            }
        });
    }

    @Override
    public void pressedCreateClan() {
        mNoClanView.showCreateClanUi();
    }

    @Override
    public void pressedJoinClan() {
        mNoClanView.showJoinClanUi();
    }

    @Override
    public void createClan(String username, String tag) {

    }

    @Override
    public void joinClan(String username, String tag, String message) {

    }

    @Override
    public void verifyCreateClan() {
        mCreateClanService.verifyCreateRequest(new CreateClanService.CreateVerificationCallback() {
            @Override
            public void onVerificationLoaded(boolean isVerified) {
                if (isVerified) {
                    mCreateClanService.deleteCreateRequest();
                    mUserService.setState(User.ADMIN);
                    mNoClanView.showHomeUi();
                } else {
                    mNoClanView.displayCreateNotVerified();
                }
            }
        });
    }

    @Override
    public void cancelCreateClan() {
        mCreateClanService.deleteCreateRequest();
        mUserService.setState(User.BLANK);
        mNoClanView.showNoClanUi();
    }

    @Override
    public void cancelJoinClan() {
        mJoinClanService.deleteJoinRequest();
        mUserService.setState(User.BLANK);
        mNoClanView.showNoClanUi();
    }

    private void loadForCreateRequest(final String clanTag) {
        mCreateClanService.getCreateRequest(new CreateClanService.CreateRequestCallback() {
            @Override
            public void onCreateRequestLoaded(final CreateRequest createRequest) {
                mApiService.getApiClan(clanTag, new ApiService.LoadApiClanCallback() {
                    @Override
                    public void onApiClanLoaded(ApiClan apiClan) {
                        mNoClanView.showAwaitingCreateUi(createRequest, apiClan);
                    }
                });
            }
        });
    }

    private void loadForJoinRequest(final String clanTag) {
        mJoinClanService.setDecisionListener(new JoinClanService.DecisionListener() {
            @Override
            public void onUpdate(JoinDecision joinDecision) {
                if (joinDecision.approved == JoinDecision.APPROVED) {
                    mClanService.checkIfAdmin(new ClanService.AdminCheckCallback() {
                        @Override
                        public void onLoaded(boolean isAdmin) {
                            if (isAdmin) {
                                mUserService.setState(User.ADMIN);
                            } else {
                                mUserService.setState(User.MEMBER);
                            }
                            mJoinClanService.deleteJoinRequest();
                            mNoClanView.showHomeUi();
                        }
                    });
                } else if (joinDecision.approved == JoinDecision.DENIED) {
                    mNoClanView.displayJoinDenied();
                } else if (joinDecision.approved == JoinDecision.PENDING) {
                    mApiService.getApiClan(clanTag, new ApiService.LoadApiClanCallback() {
                        @Override
                        public void onApiClanLoaded(ApiClan apiClan) {
                            mNoClanView.showAwaitingJoinUi(apiClan);
                        }
                    });
                }
            }
        });
    }
}
