package wickeddevs.easywars.ui.noclan.verifyjoin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import wickeddevs.easywars.data.model.JoinDecision;
import wickeddevs.easywars.data.model.User;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.CreateClanService;
import wickeddevs.easywars.data.service.contract.JoinClanService;
import wickeddevs.easywars.data.service.contract.UserService;
import wickeddevs.easywars.ui.noclan.verifycreate.VerifyCreateClanContract;

import static org.junit.Assert.*;

/**
 * Created by 375csptssce on 8/12/16.
 */
public class VerifyJoinClanPresenterTest {

    private VerifyJoinClanPresenter presenter;

    @Mock
    VerifyJoinClanContract.View view;

    @Mock
    private ApiService apiService;

    @Mock
    private JoinClanService joinClanService;

    @Mock
    private UserService userService;

    @Captor
    private ArgumentCaptor<UserService.LoadUserCallback> loadUserCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<ApiService.LoadApiClanCallback> loadApiClanCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<JoinClanService.DecisionListener> decisionListenerArgumentCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new VerifyJoinClanPresenter(apiService, joinClanService, userService);
        presenter.registerView(view);
    }

    @Test
    public void attach_joinPending_loadClanInfo_DisplayClanInfo() {
        User user = new User(User.STATE_JOINING, "#23098J");
        ApiClan apiClan = new ApiClan();
        presenter.onAttach();
        verify(joinClanService).setDecisionListener(decisionListenerArgumentCaptor.capture());
        decisionListenerArgumentCaptor.getValue().onUpdate(new JoinDecision());
        verify(userService).getUser(loadUserCallbackArgumentCaptor.capture());
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(apiService).getApiClan(eq(user.clanTag), loadApiClanCallbackArgumentCaptor.capture());
        loadApiClanCallbackArgumentCaptor.getValue().onApiClanLoaded(apiClan);
        verify(view).displayJoinInfo(apiClan);
    }

    @Test
    public void attach_joinDenied_loadClanInfo_DisplayClanInfo() {
        User user = new User(User.STATE_JOINING, "#23098J");
        ApiClan apiClan = new ApiClan();
        presenter.onAttach();
        verify(joinClanService).setDecisionListener(decisionListenerArgumentCaptor.capture());
        decisionListenerArgumentCaptor.getValue().onUpdate(new JoinDecision(false));
        verify(userService).getUser(loadUserCallbackArgumentCaptor.capture());
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(apiService).getApiClan(eq(user.clanTag), loadApiClanCallbackArgumentCaptor.capture());
        loadApiClanCallbackArgumentCaptor.getValue().onApiClanLoaded(apiClan);
        verify(view).displayJoinInfo(apiClan);
        verify(view).displayJoinDenied();
    }

    @Test
    public void attach_joinApproved_navigateToHomeUi() {
        presenter.onAttach();
        verify(joinClanService).setDecisionListener(decisionListenerArgumentCaptor.capture());
        decisionListenerArgumentCaptor.getValue().onUpdate(new JoinDecision(true));
        verify(view).navigateToHomeUi();
    }

    @Test
    public void cancelJoinRequest() {
        presenter.cancelJoinClan();
        joinClanService.removeJoinRequest();
    }
}