package com.wickeddevs.easywars.ui.joinrequests;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;

import javax.inject.Inject;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.JoinRequestsAdapter;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.Injector;
import com.wickeddevs.easywars.data.model.JoinRequest;
import com.wickeddevs.easywars.databinding.ActivityJoinRequestsBinding;

public class JoinRequestsActivity extends BasePresenterActivity<JoinRequestsContract.ViewListener> implements JoinRequestsContract.View, JoinRequestsAdapter.ApprovalListener {

    final static String TAG = "JoinRequestsActivity";

    @Inject
    public JoinRequestsContract.ViewListener presenter;
    private ActivityJoinRequestsBinding binding;

    JoinRequestsAdapter joinRequestsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_requests);
        joinRequestsAdapter = new JoinRequestsAdapter(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_requests);
        binding.rvJoinRequests.setLayoutManager(new LinearLayoutManager(this));
        binding.rvJoinRequests.setAdapter(joinRequestsAdapter);
        presenter.onCreate();
    }

    @Override
    public void addJoinRequest(JoinRequest joinRequest) {
        joinRequestsAdapter.addJoinRequest(joinRequest);
    }

    @Override
    public void toggleLoading(boolean loading) {

    }

    @Override
    public void displayNoJoinRequests() {
        binding.tvNoJoinRequests.setVisibility(View.VISIBLE);
    }

    @Override
    protected JoinRequestsContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
        }
        return presenter;
    }

    @Override
    public void onApproval(JoinRequest joinRequest, boolean approved) {
        presenter.joinRequestDecision(joinRequest, approved);
    }
}
