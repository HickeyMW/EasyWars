package wickeddevs.easywars.ui.noclan.create;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javax.inject.Inject;

import wickeddevs.easywars.R;
import wickeddevs.easywars.adapters.ClanAdapter;
import wickeddevs.easywars.adapters.ClanMembersAdapter;
import wickeddevs.easywars.base.BasePresenterActivity;
import wickeddevs.easywars.dagger.Injector;
import wickeddevs.easywars.data.model.Clan;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.databinding.ActivityCreateClanBinding;
import wickeddevs.easywars.ui.noclan.verifycreate.VerifyCreateClanActivity;

public class CreateClanActivity extends BasePresenterActivity<CreateClanContract.ViewListener>
        implements CreateClanContract.View {

    public final static String EXTRA_CLAN_TAG = "EXTRA_CLAN_TAG";

    @Inject
    public CreateClanContract.ViewListener presenter;

    private ActivityCreateClanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_clan);
        binding.rvMembers.setLayoutManager(new LinearLayoutManager(this));
        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.createClanRequest();
            }
        });
    }

    @Override
    public void displayClanInfo(ApiClan apiClan) {
        binding.layoutClan.setVisibility(View.VISIBLE);
        binding.tvClanName.setText(apiClan.name);
        binding.tvMembers.setText("Members " + apiClan.members + "/50");
        Glide.with(this).load(apiClan.badgeUrls.medium).centerCrop().into(binding.ivClanBadge);
        binding.rvMembers.setAdapter(new ClanMembersAdapter(apiClan.getMemberNames(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClanMembersAdapter clanAdapter = (ClanMembersAdapter) binding.rvMembers.getAdapter();
                String memberName = clanAdapter.getMember(binding.rvMembers.getChildLayoutPosition(view));
                presenter.selectedName(memberName);
                binding.tvSelectedName.setText(memberName);
            }
        }));


    }

    @Override
    public void allowCreate() {
        binding.btnCreate.setVisibility(View.VISIBLE);
    }

    @Override
    public String getClanTag() {
        return getIntent().getStringExtra(EXTRA_CLAN_TAG);
    }

    @Override
    public void navigateToVerifyCreateClanUi() {
        Intent i = new Intent(this, VerifyCreateClanActivity.class);
        startActivity(i);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected CreateClanContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
        }
        return presenter;
    }

    @Override
    public void toggleProgressBar(boolean loading) {
        if (loading) {
            binding.progressBar.setProgress(View.VISIBLE);
        } else {
            binding.progressBar.setProgress(View.INVISIBLE);
        }

    }

    @Override
    public void displayToast(String toast) {

    }

    public static Intent createIntent(Context context, String clanTag) {
        Intent i = new Intent(context, CreateClanActivity.class);
        i.putExtra(EXTRA_CLAN_TAG, clanTag);
        return i;
    }
}
