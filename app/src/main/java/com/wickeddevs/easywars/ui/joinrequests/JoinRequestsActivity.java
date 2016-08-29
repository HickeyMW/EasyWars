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

    private ActivityJoinRequestsBinding binding;

    @Inject
    public JoinRequestsContract.ViewListener presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_requests);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_requests);
        binding.rvJoinRequests.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void displayJoinRequests(ArrayList<JoinRequest> joinRequests) {
        binding.rvJoinRequests.setAdapter(new JoinRequestsAdapter(joinRequests, this));
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
