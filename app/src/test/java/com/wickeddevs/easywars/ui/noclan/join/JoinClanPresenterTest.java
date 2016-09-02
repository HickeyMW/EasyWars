package com.wickeddevs.easywars.ui.noclan.join;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import com.wickeddevs.easywars.data.model.JoinRequest;
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.data.model.api.ApiMember;
import com.wickeddevs.easywars.data.model.api.BadgeUrls;
import com.wickeddevs.easywars.data.service.contract.ApiService;
import com.wickeddevs.easywars.data.service.contract.JoinClanService;
import com.wickeddevs.easywars.util.Testing;

import static org.mockito.Mockito.when;

/**
 * Created by 375csptssce on 8/12/16.
 */
public class JoinClanPresenterTest {

    String clanTag = Testing.randomString();
    private ApiClan apiClan;

    private JoinClanPresenter presenter;

    @Mock
    private JoinClanContract.View view;

    @Mock
    private ApiService apiService;

    @Mock
    private JoinClanService joinClanService;

    @Captor
    private ArgumentCaptor<ApiService.LoadApiClanCallback> loadApiClanCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<JoinRequest> joinRequestArgumentCaptor;

    @Before
    public void setup() {
        apiClan = Testing.randomApiClan(clanTag);

        MockitoAnnotations.initMocks(this);
        presenter = new JoinClanPresenter(apiService, joinClanService);
        presenter.registerView(view);
    }

    @Test
    public void attach_loadClanInfo_DisplayClanInfo() {
        when(view.getClanTag()).thenReturn(clanTag);
        presenter.onCreate();
        verify(view).getClanTag();
        verify(apiService).getApiClan(eq(clanTag), loadApiClanCallbackArgumentCaptor.capture());
        loadApiClanCallbackArgumentCaptor.getValue().onApiClanLoaded(apiClan);
        verify(view).displayClanInfo(apiClan);
    }

    @Test
    public void selectedName_requestJoin_requestCreated() {
        String name = Testing.randomString();
        String message = Testing.randomString();

        attach_loadClanInfo_DisplayClanInfo();
        presenter.selectedName(name);
        verify(view).allowJoin();
        presenter.requestJoin(message);
        verify(joinClanService).setJoinRequest(eq(apiClan.tag), joinRequestArgumentCaptor.capture());
        Assert.assertEquals(joinRequestArgumentCaptor.getValue().message, message);
        Assert.assertEquals(joinRequestArgumentCaptor.getValue().name, name);
        verify(view).navigateToVerifyJoinClanUi();
    }

}