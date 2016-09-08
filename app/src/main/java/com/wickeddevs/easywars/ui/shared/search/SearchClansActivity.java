package com.wickeddevs.easywars.ui.shared.search;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import javax.inject.Inject;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.ClanAdapter;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import com.wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.databinding.ActivitySearchClansBinding;
import com.wickeddevs.easywars.ui.noclan.create.CreateClanActivity;
import com.wickeddevs.easywars.ui.noclan.join.JoinClanActivity;

public class SearchClansActivity extends BasePresenterActivity<SearchClansContract.ViewListener> implements SearchClansContract.View  {

    public final static String EXTRA_STARTED_BY = "EXTRA_STARTED_BY";
    public final static String EXTRA_CLAN_NAME = "EXTRA_CLAN_NAME";
    public final static String EXTRA_CLAN_TAG = "EXTRA_CLAN_TAG";
    public final static String EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL";
    public final static String EXTRA_CLAN_MEMBERS = "EXTRA_CLAN_MEMBERS";
    public final static int STARTED_FOR_CREATE = 0;
    public final static int STARTED_FOR_JOIN = 1;
    public final static int STARTED_FOR_WAR = 2;

    @Inject
    public SearchClansContract.ViewListener presenter;
    private ActivitySearchClansBinding binding;
    private MenuItem searchItem;
    private SearchView searchView;
    private ClanAdapter clanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_clans);
        binding.rvSearch.setLayoutManager(new LinearLayoutManager(this));
        clanAdapter = new ClanAdapter(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClanAdapter clanAdapter = (ClanAdapter) binding.rvSearch.getAdapter();
                ApiClan apiClan = clanAdapter.getClan(binding.rvSearch.getChildLayoutPosition(view));
                presenter.selectedClan(apiClan);
                if (searchView != null) {
                    searchView.clearFocus();
                }
            }
        });
        binding.rvSearch.setAdapter(clanAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setIconified(false);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search for a clan");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.search(query);
                if (searchView != null) {
                    searchView.clearFocus();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                finish();
                return true;
            }
        });
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STARTED_FOR_CREATE) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        } else if (requestCode == STARTED_FOR_JOIN) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }

    @Override
    public void displayQueryTooShort() {
        displayMessage("Searches must be at least three characters long");
    }

    @Override
    public int getStartedBy() {
        return getIntent().getIntExtra(EXTRA_STARTED_BY, -1);
    }

    @Override
    public void displaySearchResult(ArrayList<ApiClan> apiClans) {
        clanAdapter.setClans(apiClans);
    }

    @Override
    public void addClan(ApiClan apiClan) {
        clanAdapter.addClan(apiClan);
    }

    @Override
    public void clearClans() {
        clanAdapter.clearClans();
    }

    @Override
    public void navigateToCreateClanUi(String clanTag) {
        startActivityForResult(CreateClanActivity.createIntent(this, clanTag), STARTED_FOR_CREATE);

    }

    @Override
    public void navigateToJoinClanUi(String clanTag) {
        startActivityForResult(JoinClanActivity.createIntent(this, clanTag), STARTED_FOR_JOIN);
    }

    @Override
    public void navigateToWarUi(ApiClan apiClan) {
        Intent i = new Intent();
        i.putExtra(EXTRA_CLAN_NAME, apiClan.name);
        i.putExtra(EXTRA_CLAN_TAG, apiClan.tag);
        i.putExtra(EXTRA_CLAN_MEMBERS, apiClan.members);
        i.putExtra(EXTRA_IMAGE_URL, apiClan.badgeUrls.medium);
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    protected SearchClansContract.ViewListener getPresenter() {
        if(presenter == null){
            DaggerViewInjectorComponent.builder()
                    .serviceComponent(DaggerServiceComponent.create())
                    .build().inject(this);
        }
        return presenter;
    }

    @Override
    public void toggleLoading(boolean loading) {
        if (loading) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.rvSearch.setVisibility(View.INVISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.rvSearch.setVisibility(View.VISIBLE);
        }
    }

    public static Intent createIntent(Context context, int startedBy) {
        Intent i = new Intent(context, SearchClansActivity.class);
        i.putExtra(EXTRA_STARTED_BY, startedBy);
        return i;
    }

}
