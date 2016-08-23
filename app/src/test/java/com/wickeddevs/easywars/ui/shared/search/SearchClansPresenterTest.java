package com.wickeddevs.easywars.ui.shared.search;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.data.service.contract.ApiService;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by 375csptssce on 8/12/16.
 */
public class SearchClansPresenterTest {

    private SearchClansPresenter presenter;
    private ApiClan apiClan1 = new ApiClan();
    private ApiClan apiClan2 = new ApiClan();
    private ApiClan apiClan3 = new ApiClan();
    private ArrayList<ApiClan> apiClans = new ArrayList<>();

    @Mock
    private SearchClansContract.View view;

    @Mock
    private ApiService apiService;

    @Captor
    private ArgumentCaptor<ApiService.LoadApiClanCallback> loadApiClanCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<ApiService.SearchApiClansCallback> searchApiClansCallbackArgumentCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        apiClans.add(apiClan1);
        apiClans.add(apiClan2);
        apiClans.add(apiClan3);
        presenter = new SearchClansPresenter(apiService);
        presenter.registerView(view);
    }

    @Test
    public void attach_startedForJoin_displayClans() {
        when(view.getStartedBy()).thenReturn(SearchClansActivity.STARTED_FOR_JOIN);
        presenter.onAttach();
        verify(view).getStartedBy();
        verify(view).toggleProgressBar(true);
        verify(apiService).getJoinableClans(loadApiClanCallbackArgumentCaptor.capture());
        loadApiClanCallbackArgumentCaptor.getValue().onApiClanLoaded(apiClan1);
        loadApiClanCallbackArgumentCaptor.getValue().onApiClanLoaded(apiClan2);
        verify(view).clearDisplayedClans();
        verify(view).addClan(apiClan1);
        verify(view).addClan(apiClan2);
    }

    @Test
    public void search_getsSearchResults_displaysSearchResults() {
        String query = "searchedFor";
        presenter.search(query);
        verify(apiService).searchClans(eq(query), searchApiClansCallbackArgumentCaptor.capture());
        searchApiClansCallbackArgumentCaptor.getValue().onApiClansLoaded(apiClans);
        verify(view).displaySearchResult(apiClans);
    }

    @Test
    public void selectedClan_startedForCreate_Navigate() {
        when(view.getStartedBy()).thenReturn(SearchClansActivity.STARTED_FOR_CREATE);
        presenter.selectedClan(apiClan1);
        verify(view).navigateToCreateClanUi(apiClan1.tag);
    }

    @Test
    public void selectedClan_startedForJoin_Navigate() {
        when(view.getStartedBy()).thenReturn(SearchClansActivity.STARTED_FOR_JOIN);
        presenter.selectedClan(apiClan2);
        verify(view).navigateToJoinClanUi(apiClan2.tag);
    }





}