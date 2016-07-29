package wickeddevs.easywars.noclan;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.model.JoinDecision;
import wickeddevs.easywars.data.model.User;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.ClanService;
import wickeddevs.easywars.data.service.contract.CreateClanService;
import wickeddevs.easywars.data.service.contract.JoinClanService;
import wickeddevs.easywars.data.service.contract.UserService;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by 375csptssce on 7/28/16.
 */
public class NoClanPresenterTest {

    @Mock
    private NoClanContract.View mNoClanView;

    @Mock
    private UserService mUserService;

    @Mock
    private ApiService mApiService;

    @Mock
    private JoinClanService mJoinClanService;

    @Mock
    private CreateClanService mCreateClanService;

    @Mock
    private ClanService mClanService;

    @Captor
    private ArgumentCaptor<UserService.LoadUserCallback> mLoadUserCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<CreateClanService.CreateRequestCallback> mCreateRequestListenerArgumentCaptor;

    @Captor
    private ArgumentCaptor<CreateClanService.VerifyCreateCallback> mCreateVerificationCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<JoinClanService.DecisionListener> mDecisionListenerArgumentCaptor;

    @Captor
    private ArgumentCaptor<ApiService.LoadApiClanCallback> mLoadApiClanCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<ClanService.AdminCheckCallback> mAdminCheckCallbackArgumentCaptor;

    private NoClanPresenter mNoClanPresenter;

    @Before
    public void setupLoadingSplashPresenter() {
        MockitoAnnotations.initMocks(this);

        mNoClanPresenter = new NoClanPresenter(mNoClanView, mUserService, mApiService, mCreateClanService, mJoinClanService, mClanService);
    }

    @Test
    public void onStart_getUserState_isBlank_showNoClanUi() {
        User user = new User(User.BLANK, "");
        mNoClanPresenter.onStart();
        verify(mUserService).getUser(mLoadUserCallbackArgumentCaptor.capture());
        mLoadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(mNoClanView).showNoClanUi();
    }

    @Test
    public void pressedCreateClan_showCreateClanUi() {
        mNoClanPresenter.pressedCreateClan();
        verify(mNoClanView).showCreateClanUi();
    }

    @Test
    public void pressedJoinClan_showJoinClanUi() {
        mNoClanPresenter.pressedJoinClan();
        verify(mNoClanView).showJoinClanUi();
    }

    @Test
    public void onStart_getUserState_isCreating_getAndDisplayCreateRequest() {
        User user = new User(User.CREATING, "#33GF09");
        CreateRequest createRequest = new CreateRequest("Name", "#33GF09", "WEFJIO093FJ");
        ApiClan apiClan = new ApiClan();
        mNoClanPresenter.onStart();
        verify(mUserService).getUser(mLoadUserCallbackArgumentCaptor.capture());
        mLoadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(mCreateClanService).getCreateRequest(mCreateRequestListenerArgumentCaptor.capture());
        mCreateRequestListenerArgumentCaptor.getValue().onCreateRequestLoaded(createRequest);
        verify(mApiService).getApiClan(eq(createRequest.tag), mLoadApiClanCallbackArgumentCaptor.capture());
        mLoadApiClanCallbackArgumentCaptor.getValue().onApiClanLoaded(apiClan);
        verify(mNoClanView).showAwaitingCreateUi(createRequest, apiClan);
    }

    @Test
    public void pressedVerify_isVerified_displayNotVerified() {
        mNoClanPresenter.verifyCreateClan();
        verify(mCreateClanService).verifyCreateRequest(mCreateVerificationCallbackArgumentCaptor.capture());
        mCreateVerificationCallbackArgumentCaptor.getValue().onVerificationLoaded(true);
        verify(mCreateClanService).deleteCreateRequest();
        verify(mUserService).setState(User.ADMIN);
        verify(mNoClanView).showHomeUi();
    }

    @Test
    public void pressedVerify_isNotVerified_displayNotVerified() {
        mNoClanPresenter.verifyCreateClan();
        verify(mCreateClanService).verifyCreateRequest(mCreateVerificationCallbackArgumentCaptor.capture());
        mCreateVerificationCallbackArgumentCaptor.getValue().onVerificationLoaded(false);
        verify(mNoClanView).displayCreateNotVerified();
    }

    @Test
    public void onStart_getUserState_isJoining_decisionPending_getAndDisplayJoinClanInfo() {
        String tag = "#W5OE46I";
        User user = new User(User.JOINING, tag);
        JoinDecision joinDecision = new JoinDecision();
        ApiClan apiClan = new ApiClan();
        mNoClanPresenter.onStart();
        verify(mUserService).getUser(mLoadUserCallbackArgumentCaptor.capture());
        mLoadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(mJoinClanService).setDecisionListener(mDecisionListenerArgumentCaptor.capture());
        mDecisionListenerArgumentCaptor.getValue().onUpdate(joinDecision);
        verify(mApiService).getApiClan(eq(tag), mLoadApiClanCallbackArgumentCaptor.capture());
        mLoadApiClanCallbackArgumentCaptor.getValue().onApiClanLoaded(apiClan);
        verify(mNoClanView).showAwaitingJoinUi(apiClan);
    }

    @Test
    public void onStart_getUserState_isJoining_decisionApprovedMember_checkifAdminSetUserState_showHomeUi() {
        String tag = "#W5OE46I";
        User user = new User(User.JOINING, tag);
        JoinDecision joinDecision = new JoinDecision(true);
        mNoClanPresenter.onStart();
        verify(mUserService).getUser(mLoadUserCallbackArgumentCaptor.capture());
        mLoadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(mJoinClanService).setDecisionListener(mDecisionListenerArgumentCaptor.capture());
        mDecisionListenerArgumentCaptor.getValue().onUpdate(joinDecision);
        verify(mClanService).checkIfAdmin(mAdminCheckCallbackArgumentCaptor.capture());
        mAdminCheckCallbackArgumentCaptor.getValue().onLoaded(false);
        verify(mUserService).setState(User.MEMBER);
        verify(mJoinClanService).deleteJoinRequest();
        verify(mNoClanView).showHomeUi();
    }

    @Test
    public void onStart_getUserState_isJoining_decisionApprovedAdmin_checkifAdminSetUserState_showHomeUi() {
        String tag = "#W5OE46I";
        User user = new User(User.JOINING, tag);
        JoinDecision joinDecision = new JoinDecision(true);
        mNoClanPresenter.onStart();
        verify(mUserService).getUser(mLoadUserCallbackArgumentCaptor.capture());
        mLoadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(mJoinClanService).setDecisionListener(mDecisionListenerArgumentCaptor.capture());
        mDecisionListenerArgumentCaptor.getValue().onUpdate(joinDecision);
        verify(mClanService).checkIfAdmin(mAdminCheckCallbackArgumentCaptor.capture());
        mAdminCheckCallbackArgumentCaptor.getValue().onLoaded(true);
        verify(mUserService).setState(User.ADMIN);
        verify(mJoinClanService).deleteJoinRequest();
        verify(mNoClanView).showHomeUi();
    }

    @Test
    public void onStart_getUserState_isJoining_decisionDenied_displayDenied() {
        String tag = "#W5OE46I";
        User user = new User(User.JOINING, tag);
        JoinDecision joinDecision = new JoinDecision(false);
        mNoClanPresenter.onStart();
        verify(mUserService).getUser(mLoadUserCallbackArgumentCaptor.capture());
        mLoadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(mJoinClanService).setDecisionListener(mDecisionListenerArgumentCaptor.capture());
        mDecisionListenerArgumentCaptor.getValue().onUpdate(joinDecision);
        verify(mNoClanView).displayJoinDenied();

    }
}