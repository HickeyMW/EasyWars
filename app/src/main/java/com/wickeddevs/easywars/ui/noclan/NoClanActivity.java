package com.wickeddevs.easywars.ui.noclan;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.databinding.ActivityNoClanBinding;
import com.wickeddevs.easywars.ui.loadingsplash.LoadingSplashActivity;
import com.wickeddevs.easywars.ui.shared.search.SearchClansActivity;

public class NoClanActivity extends AppCompatActivity {

    final static String TAG = "NoClanActivity";

    private ActivityNoClanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_no_clan);
        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SearchClansActivity.createIntent(NoClanActivity.this, SearchClansActivity.STARTED_FOR_CREATE));

            }
        });
        binding.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SearchClansActivity.createIntent(NoClanActivity.this, SearchClansActivity.STARTED_FOR_JOIN));
            }
        });
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(NoClanActivity.this, LoadingSplashActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
