package com.wickeddevs.easywars.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javax.inject.Inject;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import com.wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.miscellaneous.CloseKeyboardDrawerListener;
import com.wickeddevs.easywars.miscellaneous.HideProgressBarRequestListener;
import com.wickeddevs.easywars.ui.TestingActivity;
import com.wickeddevs.easywars.ui.home.chat.ChatViewPagerFragment;
import com.wickeddevs.easywars.ui.home.war.WarViewPagerFragment;
import com.wickeddevs.easywars.ui.joinrequests.JoinRequestsActivity;
import com.wickeddevs.easywars.ui.loadingsplash.LoadingSplashActivity;
import com.wickeddevs.easywars.ui.noclan.NoClanActivity;
import com.wickeddevs.easywars.ui.notdone.NotImplementedActivity;
import com.wickeddevs.easywars.ui.notdone.NotImplementedFragment;

public class HomeActivity extends BasePresenterActivity<HomeContract.ViewListener> implements
        HomeContract.View, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";
    private static final String EXTRA_IS_ADMIN = "EXTRA_IS_ADMIN";

    private ArrayList<MenuItem> adminItems = new ArrayList<>();
    private DrawerLayout drawer;

    private boolean isAdmin;

    @Inject
    public HomeContract.ViewListener presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.addDrawerListener(new CloseKeyboardDrawerListener(this));
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        adminItems.add(navigationView.getMenu().findItem(R.id.nav_member_manager));
        adminItems.add(navigationView.getMenu().findItem(R.id.nav_join_requests));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_chat:
                        setTitle("Chat");
                        getSupportActionBar().setSubtitle("");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_home, ChatViewPagerFragment.getInstance(isAdmin)).commit();
                        break;
                    case R.id.tab_war:
                        setTitle("War Planner");
                        getSupportActionBar().setSubtitle("");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_home, WarViewPagerFragment.getInstance(isAdmin)).commit();
                        break;
                    case R.id.tab_notifications:
                        setTitle("Notifications");
                        getSupportActionBar().setSubtitle("");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_home, new NotImplementedFragment()).commit();
                        break;
                }
            }
        });

        isAdmin = getIntent().getBooleanExtra(EXTRA_IS_ADMIN, false);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_home, ChatViewPagerFragment.getInstance(isAdmin)).commit();

        if (isAdmin) {
            for (MenuItem menuItem : adminItems) {
                menuItem.setVisible(true);
            }
        }

        presenter.onCreate();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            finishAffinity();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_war_history) {
            Intent i = new Intent(this, NotImplementedActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_member_manager) {
            Intent i = new Intent(this, NotImplementedActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_join_requests) {
            Intent i = new Intent(this, JoinRequestsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_settings) {
            Intent i = new Intent(this, NotImplementedActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_issue_tracker) {
            Intent i = new Intent(this, NotImplementedActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            presenter.pressedLogout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected HomeContract.ViewListener getPresenter() {
        if(presenter == null){
            DaggerViewInjectorComponent.builder()
                    .serviceComponent(DaggerServiceComponent.create())
                    .build().inject(this);
        }
        return presenter;
    }

    @Override
    public void displayUi(Member member, ApiClan apiClan) {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        TextView headerName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvHeaderName);
        TextView headerClan = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvHeaderClan);
        ImageView headerImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.ivHeaderImage);
        final ProgressBar progressBar = (ProgressBar) navigationView.getHeaderView(0).findViewById(R.id.headerProgressBar);
        headerName.setText(member.name);
        headerClan.setText(apiClan.name);
        Glide.with(this).load(apiClan.badgeUrls.medium).centerCrop().listener(new HideProgressBarRequestListener(progressBar)).into(headerImage);
    }

    @Override
    public void navigateToNoClanUi() {
        Intent i = new Intent(this, NoClanActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void navigateToLoadingSplash() {
        Intent i = new Intent(this, LoadingSplashActivity.class);
        startActivity(i);
        finish();
    }


    public static Intent getInstance(Context context, boolean isAdmin) {
        Intent i = new Intent(context, HomeActivity.class);
        i.putExtra(EXTRA_IS_ADMIN, isAdmin);
        return i;
    }
}
