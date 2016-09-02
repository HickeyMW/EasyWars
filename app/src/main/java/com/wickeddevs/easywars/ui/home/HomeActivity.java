package com.wickeddevs.easywars.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javax.inject.Inject;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.Injector;
import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.ui.TestingActivity;
import com.wickeddevs.easywars.ui.home.chat.ChatFragment;
import com.wickeddevs.easywars.ui.home.war.WarPlannerFragment;
import com.wickeddevs.easywars.ui.joinrequests.JoinRequestsActivity;
import com.wickeddevs.easywars.ui.loadingsplash.LoadingSplashActivity;
import com.wickeddevs.easywars.ui.noclan.NoClanActivity;
import com.wickeddevs.easywars.util.Shared;

public class HomeActivity extends BasePresenterActivity<HomeContract.ViewListener> implements
        HomeContract.View, NavigationView.OnNavigationItemSelectedListener {

    final static String TAG = "HomeActivity";
    static final String EXTRA_IS_ADMIN  = "extraIsAdmin";
    private ArrayList<MenuItem> adminItems = new ArrayList<>();
    private DrawerLayout drawer;
    private MenuItem settingsItem;

    @Inject
    public HomeContract.ViewListener presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                Shared.hideKeyboard(HomeActivity.this);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        toggle.syncState();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        adminItems.add(navigationView.getMenu().findItem(R.id.nav_join_requests));
        adminItems.add(navigationView.getMenu().findItem(R.id.nav_admin_chat));
        setTitle("Chat");
        navigationView.getMenu().findItem(R.id.nav_chat).setChecked(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_home, ChatFragment.getInstance(false)).commit();
        presenter.onCreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        settingsItem = menu.findItem(R.id.action_settings);
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Log.i(TAG, "onOptionsItemSelected: Pressed it");
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_chat) {
            setTitle("Chat");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_home, ChatFragment.getInstance(false)).commit();
        } if (id == R.id.nav_admin_chat) {
            setTitle("Admin Chat");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_home, ChatFragment.getInstance(true)).commit();
        } else if (id == R.id.nav_war_planner) {
            setTitle("War Against Other Clan");
            getSupportActionBar().setSubtitle("Time Remining: 5:35");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_home, new WarPlannerFragment()).commit();
            settingsItem.setVisible(true);
        } else if (id == R.id.nav_join_requests) {
            Intent i = new Intent(this, JoinRequestsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_test) {
            Intent i = new Intent(this, TestingActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_logout) {
            presenter.pressedLogout();
        } else {
            Log.i(TAG, "We don't navigate anywhere else");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected HomeContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
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
        Glide.with(this).load(apiClan.badgeUrls.medium).centerCrop().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar.setVisibility(View.INVISIBLE);
                return false;
            }
        }).into(headerImage);

        if (member.admin) {
            for (MenuItem menuItem : adminItems) {
                menuItem.setVisible(true);
            }
        }
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
}
