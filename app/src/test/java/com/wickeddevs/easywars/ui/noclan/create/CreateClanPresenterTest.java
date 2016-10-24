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
import com.wickeddevs.easywars.data.service.contract.ApiService;
import com.wickeddevs.easywars.data.service.contract.CreateClanService;
import com.wickeddevs.easywars.util.Testing;


/**
 * Created by hicke_000 on 7/27/2016.
 */
public class CreateClanPresenterTest {

    String clanTag = Testing.randomString();
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
        apiClan = Testing.randomApiClan(clanTag);

        MockitoAnnotations.initMocks(this);
        presenter = new CreateClanPresenter(apiService, createClanService);
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
    public void selectedName_createRequest_requestCreated() {
        String name = Testing.randomString();

        attach_loadClanInfo_DisplayClanInfo();
        presenter.selectedName(name);
        verify(view).allowCreate();
        presenter.createClanRequest();
        //verify(createClanService).setCreateRequest(name, apiClan.tag);
        verify(view).navigateToVerifyCreateClanUi();
    }
}