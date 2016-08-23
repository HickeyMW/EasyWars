package com.wickeddevs.easywars.ui.noclan.create;

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
import static org.mockito.Mockito.when;

import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.data.model.api.ApiMember;
import com.wickeddevs.easywars.data.model.api.BadgeUrls;
import com.wickeddevs.easywars.data.service.contract.ApiService;
import com.wickeddevs.easywars.data.service.contract.CreateClanService;


/**
 * Created by hicke_000 on 7/27/2016.
 */
public class CreateClanPresenterTest {

    private String tag1 = "#FOI30FJ";
    private ApiMember apiMember1 = new ApiMember("Mem1Name", "Mem1Tag");
    private ApiMember apiMember2 = new ApiMember("Mem2Name", "Mem2Tag");
    private ArrayList<ApiMember> apiMembers = new ArrayList<>();
    private ApiClan apiClan;

    private CreateClanPresenter presenter;

    @Mock
    private CreateClanContract.View view;

    @Mock
    private ApiService apiService;

    @Mock
    private CreateClanService createClanService;

    @Captor
    private ArgumentCaptor<ApiService.LoadApiClanCallback> loadApiClanCallbackArgumentCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new CreateClanPresenter(apiService, createClanService);
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
    public void selectedName_createRequest_requestCreated() {
        attach_loadClanInfo_DisplayClanInfo();
        String name = "selectedName";
        presenter.selectedName(name);
        verify(view).allowCreate();
        presenter.createClanRequest();
        verify(createClanService).setCreateRequest(name, apiClan.tag);
        verify(view).navigateToVerifyCreateClanUi();
    }
}