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
import com.wickeddevs.easywars.data.service.contract.JoinClanService;
import com.wickeddevs.easywars.util.Testing;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by 375csptssce on 8/12/16.
 */
public class SearchClansPresenterTest {

    private SearchClansPresenter presenter;
    private ApiClan apiClan = Testing.randomApiClan();
    private String query = Testing.randomString();

    @Mock
    private SearchClansContract.View view;

    @Mock
    private ApiService apiService;

    @Mock
    private JoinClanService joinClanService;

    @Captor
    private ArgumentCaptor<JoinClanService.ClanTagsCallback> clanTagsCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<ApiService.SearchApiClansCallback> searchApiClansCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<ApiService.LoadApiClanCallback> loadApiClanCallbackArgumentCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new SearchClansPresenter(apiService, joinClanService);
        presenter.registerView(view);
    }

    @Test
    public void searchJoinableClans() {
        when(view.getStartedBy()).thenReturn(SearchClansActivity.STARTED_FOR_JOIN);
        presenter.search(query);
        view.clearClans();
        view.toggleLoading(true);
        verify(joinClanService).searchJoinableClans(eq(query), clanTagsCallbackArgumentCaptor.capture());
    }

    @Test
    public void searchJoinableClans_clansFound_displayClans() {
        ArrayList<String> clanTags = Testing.randomStringList();

        searchJoinableClans();
        clanTagsCallbackArgumentCaptor.getValue().onLoaded(clanTags);
        view.toggleLoading(false);
        for (String clanTag : clanTags) {
            verify(apiService).getApiClan(eq(clanTag), loadApiClanCallbackArgumentCaptor.capture());
            ApiClan apiClan = Testing.randomApiClan(clanTag);
            loadApiClanCallbackArgumentCaptor.getValue().onApiClanLoaded(apiClan);
            view.addClan(apiClan);
        }
    }

    @Test
    public void searchJoinableClans_noClansFound_displayClans() {
        ArrayList<String> emptyClanTags = new ArrayList<String>();

        searchJoinableClans();
        clanTagsCallbackArgumentCaptor.getValue().onLoaded(emptyClanTags);
        view.toggleLoading(false);
        view.displayMessage(anyString());
    }

    @Test
    public void search_getsSearchResults_displaysSearchResults() {
        ArrayList<ApiClan> apiClans = Testing.randomApiClanList();

        presenter.search(query);
        verify(apiService).searchClans(eq(query), searchApiClansCallbackArgumentCaptor.capture());
        searchApiClansCallbackArgumentCaptor.getValue().onApiClansLoaded(apiClans);
        verify(view).displaySearchResult(apiClans);
    }

    @Test
    public void search_queryLessThanThreeCharacters_displayWarning() {
        String tooShortQuery = "jj";
        presenter.search(tooShortQuery);
        verify(view).displayQueryTooShort();
    }

    @Test
    public void selectedClan_startedForCreate_Navigate() {
        when(view.getStartedBy()).thenReturn(SearchClansActivity.STARTED_FOR_CREATE);
        presenter.selectedClan(apiClan);
        verify(view).navigateToCreateClanUi(apiClan.tag);
    }

    @Test
    public void selectedClan_startedForJoin_Navigate() {
        when(view.getStartedBy()).thenReturn(SearchClansActivity.STARTED_FOR_JOIN);
        presenter.selectedClan(apiClan);
        verify(view).navigateToJoinClanUi(apiClan.tag);
    }

    @Test
    public void selectedClan_startedForWar_Navigate() {
        when(view.getStartedBy()).thenReturn(SearchClansActivity.STARTED_FOR_WAR);
        presenter.selectedClan(apiClan);
        verify(view).navigateToWarUi(apiClan);
    }
}