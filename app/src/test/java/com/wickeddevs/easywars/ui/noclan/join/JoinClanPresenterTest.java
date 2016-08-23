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

import static org.mockito.Mockito.when;

/**
 * Created by 375csptssce on 8/12/16.
 */
public class JoinClanPresenterTest {

    private String tag1 = "#FOI30FJ";
    private ApiMember apiMember1 = new ApiMember("Mem1Name", "Mem1Tag");
    private ApiMember apiMember2 = new ApiMember("Mem2Name", "Mem2Tag");
    private ArrayList<ApiMember> apiMembers = new ArrayList<>();
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
        MockitoAnnotations.initMocks(this);
        presenter = new JoinClanPresenter(apiService, joinClanService);
        presenter.registerView(view);
        apiMembers.add(apiMember1);
        apiMembers.add(apiMember2);
        apiClan = new ApiClan("Clan Name", "ClanTag", new BadgeUrls(), 2, apiMembers);
    }

    @Test
    public void attach_loadClanInfo_DisplayClanInfo() {
        when(view.getClanTag()).thenReturn(tag1);
        presenter.onAttach();
        verify(view).getClanTag();
        verify(apiService).getApiClan(eq(tag1), loadApiClanCallbackArgumentCaptor.capture());
        loadApiClanCallbackArgumentCaptor.getValue().onApiClanLoaded(apiClan);
        verify(view).displayClanInfo(apiClan);
    }

    @Test
    public void selectedName_requestJoin_requestCreated() {
        attach_loadClanInfo_DisplayClanInfo();
        String name = "selectedName";
        String message = "Message sent with request";
        presenter.selectedName(name);
        verify(view).allowJoin();
        presenter.requestJoin(message);
        verify(joinClanService).setJoinRequest(eq(apiClan.tag), joinRequestArgumentCaptor.capture());
        Assert.assertEquals(joinRequestArgumentCaptor.getValue().message, message);
        Assert.assertEquals(joinRequestArgumentCaptor.getValue().name, name);
        verify(view).navigateToVerifyJoinClanUi();
    }

}