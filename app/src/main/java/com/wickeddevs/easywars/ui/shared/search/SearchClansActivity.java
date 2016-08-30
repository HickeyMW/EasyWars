package com.wickeddevs.easywars.ui.shared.search;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.ClanAdapter;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.Injector;
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.databinding.ActivitySearchClansBinding;
import com.wickeddevs.easywars.ui.noclan.create.CreateClanActivity;
import com.wickeddevs.easywars.ui.noclan.join.JoinClanActivity;

public class SearchClansActivity extends BasePresenterActivity<SearchClansContract.ViewListener> implements SearchClansContract.View  {

    public final static String EXTRA_STARTED_BY = "EXTRA_STARTED_BY";
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
        Toast.makeText(this, "Searches must be at least three characters long", Toast.LENGTH_LONG).show();
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
        i.putExtra("clanName", apiClan.name);
        i.putExtra("clanTag", apiClan.tag);
        i.putExtra("imageUrl", apiClan.badgeUrls.medium);
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    protected SearchClansContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
        }
        return presenter;
    }

    @Override
    public void toggleLoading(boolean loading) {
        if (loading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public static Intent createIntent(Context context, int startedBy) {
        Intent i = new Intent(context, SearchClansActivity.class);
        i.putExtra(EXTRA_STARTED_BY, startedBy);
        return i;
    }

}