package wickeddevs.easywars.ui.noclan.verifycreate;

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

import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.CreateClanService;
import wickeddevs.easywars.ui.noclan.create.CreateClanContract;

import static org.junit.Assert.*;

/**
 * Created by 375csptssce on 8/12/16.
 */
public class VerifyCreateClanPresenterTest {

    private VerifyCreateClanPresenter presenter;

    @Mock
    private VerifyCreateClanContract.View view;

    @Mock
    private ApiService apiService;

    @Mock
    private CreateClanService createClanService;

    @Captor
    private ArgumentCaptor<CreateClanService.CreateRequestCallback> createRequestCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<ApiService.LoadApiClanCallback> loadApiClanCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<CreateClanService.VerifyCreateCallback> verifyCreateCallbackArgumentCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new VerifyCreateClanPresenter(apiService, createClanService);
        presenter.registerView(view);
    }

    @Test
    public void attach_loadRequestAndClanInfo_displayData() {
        CreateRequest createRequest = new CreateRequest("name", "#3FO38F");
        ApiClan apiClan = new ApiClan();
        presenter.onAttach();
        verify(view).toggleProgressBar(true);
        verify(createClanService).getCreateRequest(createRequestCallbackArgumentCaptor.capture());
        createRequestCallbackArgumentCaptor.getValue().onCreateRequestLoaded(createRequest);
        verify(apiService).getApiClan(eq(createRequest.tag), loadApiClanCallbackArgumentCaptor.capture());
        loadApiClanCallbackArgumentCaptor.getValue().onApiClanLoaded(apiClan);
        verify(view).toggleProgressBar(false);
        verify(view).displayCreateRequestDetails(createRequest, apiClan);
    }

    @Test
    public void verify_isVerified_navigateHome() {
        presenter.verifyCreateClan();
        verify(createClanService).verifyCreateRequest(verifyCreateCallbackArgumentCaptor.capture());
        verifyCreateCallbackArgumentCaptor.getValue().onVerificationLoaded(true);
        verify(view).navigateToHomeUi();
    }

    @Test
    public void verify_isNotVerified_displayErrorMessage() {
        presenter.verifyCreateClan();
        verify(createClanService).verifyCreateRequest(verifyCreateCallbackArgumentCaptor.capture());
        verifyCreateCallbackArgumentCaptor.getValue().onVerificationLoaded(false);
        verify(view).displayMessage(anyString());
    }

    @Test
    public void verify_triedAgainTooSoon_DisplayErrorMessage() {
        presenter.verifyCreateClan();
        verify(createClanService).verifyCreateRequest(verifyCreateCallbackArgumentCaptor.capture());
        verifyCreateCallbackArgumentCaptor.getValue().onVerificationLoaded(false);
        presenter.verifyCreateClan();
        verify(view, times(2)).displayMessage(anyString());
    }

    @Test
    public void cancelCreate_removeCreateRequest_navigateToNoClan() {
        presenter.cancelCreateClan();
        verify(createClanService).deleteCreateRequest();
        verify(view).navigateToNoClanUi();
    }
}