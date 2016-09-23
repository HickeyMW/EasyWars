package com.wickeddevs.easywars.ui.membersmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.recyclerview.MembersRVA;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import com.wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.easywars.data.model.Member;

import java.util.ArrayList;

import javax.inject.Inject;

public class MembersManagerActivity extends BasePresenterActivity<MembersManagerContract.ViewListener> implements
        MembersManagerContract.View {

    TextView tvName;
    Switch swchAdmin;
    RecyclerView rvMembers;
    ProgressBar progressBar;
    MembersRVA membersRVA;

    @Inject
    public MembersManagerContract.ViewListener presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_manager);
        tvName = (TextView) findViewById(R.id.tvName);
        swchAdmin = (Switch) findViewById(R.id.swchAdmin);
        rvMembers = (RecyclerView) findViewById(R.id.rvMembers);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rvMembers.setLayoutManager(new LinearLayoutManager(this));
        swchAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.toggledAdmin(isChecked);
            }
        });
        presenter.onCreate();
    }

    @Override
    protected MembersManagerContract.ViewListener getPresenter() {
        if(presenter == null){
            DaggerViewInjectorComponent.builder()
                    .serviceComponent(DaggerServiceComponent.create())
                    .build().inject(this);
        }
        return presenter;
    }

    @Override
    public void displayMembers(final ArrayList<Member> members) {
        membersRVA = new MembersRVA(members, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = rvMembers.getChildLayoutPosition(v);
                Member member = membersRVA.getMember(position);
                presenter.selectedMember(member);
                swchAdmin.setChecked(member.admin);
                tvName.setText(member.name);
                swchAdmin.setVisibility(View.VISIBLE);
            }
        });
        rvMembers.setAdapter(membersRVA);
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
