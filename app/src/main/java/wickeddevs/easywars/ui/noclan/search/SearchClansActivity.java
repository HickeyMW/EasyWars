package wickeddevs.easywars.ui.noclan.search;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import javax.inject.Inject;

import wickeddevs.easywars.R;
import wickeddevs.easywars.adapters.ClanAdapter;
import wickeddevs.easywars.base.BasePresenterActivity;
import wickeddevs.easywars.dagger.Injector;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.databinding.ActivitySearchClansBinding;
import wickeddevs.easywars.ui.noclan.create.CreateClanActivity;
import wickeddevs.easywars.ui.noclan.join.JoinClanActivity;

public class SearchClansActivity extends BasePresenterActivity<SearchClansContract.ViewListener> implements SearchClansContract.View  {

    public final static String EXTRA_STARTED_BY = "EXTRA_STARTED_BY";
    public final static int STARTED_FOR_CREATE = 0;
    public final static int STARTED_FOR_JOIN = 1;
    public final static int STARTED_FOR_WAR = 2;

    @Inject
    public SearchClansContract.ViewListener presenter;
    private ActivitySearchClansBinding binding;
    private MenuItem searchItem;
    private View.OnClickListener onClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_clans);
        binding.rvSearch.setLayoutManager(new LinearLayoutManager(this));
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClanAdapter clanAdapter = (ClanAdapter) binding.rvSearch.getAdapter();
                ApiClan apiClan = clanAdapter.getClan(binding.rvSearch.getChildLayoutPosition(view));
                presenter.selectedClan(apiClan);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchItem.expandActionView();
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search for a clan");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.search(query);
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
            };
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
    public int getStartedBy() {
        return getIntent().getIntExtra(EXTRA_STARTED_BY, -1);
    }

    @Override
    public void displaySearchResult(ArrayList<ApiClan> apiClans) {
        binding.rvSearch.setAdapter(new ClanAdapter(apiClans, this, onClickListener));
    }

    @Override
    public void clearDisplayedClans() {
        binding.rvSearch.setAdapter(new ClanAdapter(this, onClickListener));
    }

    @Override
    public void addClan(ApiClan apiClan) {
        ClanAdapter clanAdapter = (ClanAdapter) binding.rvSearch.getAdapter();
        clanAdapter.addClan(apiClan);
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
    protected SearchClansContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
        }
        return presenter;
    }

    @Override
    public void toggleProgressBar(boolean loading) {

    }

    @Override
    public void displayMessage(String message) {

    }

    public static Intent createIntent(Context context, int startedBy) {
        Intent i = new Intent(context, SearchClansActivity.class);
        i.putExtra(EXTRA_STARTED_BY, startedBy);
        return i;
    }

}
