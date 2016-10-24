package com.wickeddevs.easywars.ui.noclan.verifyjoin;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import com.wickeddevs.easywars.data.model.JoinDecision;
import com.wickeddevs.easywars.data.model.User;
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.data.service.contract.ApiService;
import com.wickeddevs.easywars.data.service.contract.JoinClanService;
import com.wickeddevs.easywars.data.service.contract.UserService;
import com.wickeddevs.easywars.util.Testing;

/**
 * Created by 375csptssce on 8/12/16.
 */
public class VerifyJoinClanPresenterTest {

    private ApiClan apiClan = Testing.randomApiClan();
    private User user = Testing.randomUser(User.STATE_JOINING);

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
    public void attach_getDecision() {
        presenter.onCreate();
        view.toggleLoading(true);
        verify(joinClanService).setDecisionListener(decisionListenerArgumentCaptor.capture());
    }

    @Test
    public void attach_getDecision_joinPending_loadClanInfo_displayClanInfo() {
        attach_getDecision();
        decisionListenerArgumentCaptor.getValue().onUpdate(new JoinDecision());
        helper_loadUser_loadClan_displayInfo();
    }

    @Test
    public void attach_getDecision_joinDenied_loadClanInfo_displayClanInfo_displayDenied() {
        attach_getDecision();
        decisionListenerArgumentCaptor.getValue().onUpdate(new JoinDecision(false));
        helper_loadUser_loadClan_displayInfo();
        verify(view).displayJoinDenied();
    }

    private void helper_loadUser_loadClan_displayInfo() {
        verify(userService).getUser(loadUserCallbackArgumentCaptor.capture());
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(apiService).getApiClan(eq(user.clanTag), loadApiClanCallbackArgumentCaptor.capture());
        loadApiClanCallbackArgumentCaptor.getValue().onApiClanLoaded(apiClan);
        verify(view).toggleLoading(false);
        verify(view).displayJoinInfo(apiClan);
    }

    @Test
    public void attach_getDecision_joinApproved_navigateToHomeUi() {
        attach_getDecision();
        decisionListenerArgumentCaptor.getValue().onUpdate(new JoinDecision(true));
        verify(view).navigateToHomeUi();
    }

    @Test
    public void cancelJoinRequest() {
        presenter.cancelJoinClan();
        //joinClanService.removeJoinRequest();
    }
}