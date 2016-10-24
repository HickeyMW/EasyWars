package com.wickeddevs.easywars.ui.warsettings;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.recyclerview.ParticipentNamesRVA;
import com.wickeddevs.easywars.adapters.recyclerview.ParticipentRVA;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import com.wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.easywars.data.model.war.Participent;
import com.wickeddevs.easywars.ui.attacks.AttacksActivity;
import com.wickeddevs.easywars.ui.home.HomeContract;

import java.util.ArrayList;

import javax.inject.Inject;

public class WarSettingsActivity extends BasePresenterActivity<WarSettingsContract.ViewListener> implements
        WarSettingsContract.View {

    @Inject
    public WarSettingsContract.ViewListener presenter;

    RecyclerView rvMembers;
    Button btnDeleteWar;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_war_settings);

        rvMembers = (RecyclerView) findViewById(R.id.rvMembers);
        btnDeleteWar = (Button) findViewById(R.id.btnDeleteWar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        rvMembers.setLayoutManager(new LinearLayoutManager(this));
        btnDeleteWar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(WarSettingsActivity.this)
                        .setTitle("Delete War")
                        .setMessage("Are you sure you want to delete the current war? This cannot be undone.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.deleteWar();
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        presenter.onCreate();
    }

    @Override
    protected WarSettingsContract.ViewListener getPresenter() {
        if(presenter == null){
            DaggerViewInjectorComponent.builder()
                    .serviceComponent(DaggerServiceComponent.create())
                    .build().inject(this);
        }
        return presenter;
    }

    @Override
    public void displayParticipents(final ArrayList<Participent> participents) {
        rvMembers.setAdapter(new ParticipentNamesRVA(participents, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = rvMembers.getChildLayoutPosition(v);
                Participent participent = participents.get(position);
                String uid;
                if (participent.uid == null) {
                    uid = participent.name;
                } else {
                    uid = participent.uid;
                }
                startActivity(AttacksActivity.createIntent(WarSettingsActivity.this, uid));
            }
        }));
    }

    @Override
    public void toggleLoading(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
            rvMembers.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            rvMembers.setVisibility(View.VISIBLE);
        }
    }
}
