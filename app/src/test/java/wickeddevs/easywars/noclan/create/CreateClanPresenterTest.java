package wickeddevs.easywars.noclan.create;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.CreateClanService;
import wickeddevs.easywars.data.service.contract.UserService;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class CreateClanPresenterTest {

    private static String sName = "Random Name";
    private static String sUid = "239F82H39SKJ";
    private static ApiClan sApiClan = new ApiClan();
    private static CreateRequest sCreateRequest;
    @Mock
    private CreateClanContract.View mCreateClanView;

    @Mock
    private ApiService mApiService;

    @Mock
    private CreateClanService mCreateClanService;

    @Mock
    private UserService mUserService;

    @Captor
    private ArgumentCaptor<ApiService.SearchApiClansCallback> mSearchApiClansCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<ApiService.LoadApiClanCallback> mLoadApiClanCallbackArgumentCaptor;

    private CreateClanPresenter mCreateClanPresenter;


    @Before
    public void setupLoadingSplashPresenter() {
        MockitoAnnotations.initMocks(this);
        sApiClan.tag = "#OI0fF8J";
        sCreateRequest = new CreateRequest(sName, sApiClan.tag, sUid);
        mCreateClanPresenter = new CreateClanPresenter(mCreateClanView, mApiService, mCreateClanService, mUserService);
    }

    @Test
    public void queryRecieved_returnResults() {
        String query = "Clan name";
        ArrayList<ApiClan> apiClans = new ArrayList<>();
        mCreateClanPresenter.search(query);
        verify(mApiService).searchClans(eq(query), mSearchApiClansCallbackArgumentCaptor.capture());
        mSearchApiClansCallbackArgumentCaptor.getValue().onApiClansLoaded(apiClans);
        verify(mCreateClanView).showSearchResult(apiClans);
    }

    @Test
    public void selectedClan_displayDetailedClanInfo() {
        ApiClan apiClanDetailed = new ApiClan();
        mCreateClanPresenter.selectedClan(sApiClan);
        verify(mApiService).getApiClan(eq(sApiClan.tag), mLoadApiClanCallbackArgumentCaptor.capture());
        mLoadApiClanCallbackArgumentCaptor.getValue().onApiClanLoaded(apiClanDetailed);
        verify(mCreateClanView).showDetailedClan(apiClanDetailed);
    }

    @Test
    public void selectNameCreateClan() {
        mCreateClanPresenter.selectedName(sName);
        mCreateClanPresenter.createClan();
        verify(mCreateClanService).setCreateRequest(sCreateRequest);
    }
}