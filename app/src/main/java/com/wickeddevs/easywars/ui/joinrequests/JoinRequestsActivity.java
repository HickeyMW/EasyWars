package com.wickeddevs.easywars.ui.joinrequests;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import javax.inject.Inject;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.recyclerview.JoinRequestsRVA;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import com.wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.easywars.data.model.JoinRequest;
import com.wickeddevs.easywars.databinding.ActivityJoinRequestsBinding;

public class JoinRequestsActivity extends BasePresenterActivity<JoinRequestsContract.ViewListener> implements JoinRequestsContract.View, JoinRequestsRVA.ApprovalListener {

    final static String TAG = "JoinRequestsActivity";

    @Inject
    public JoinRequestsContract.ViewListener presenter;
    private ActivityJoinRequestsBinding binding;

    JoinRequestsRVA joinRequestsRVA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_requests);
        joinRequestsRVA = new JoinRequestsRVA(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_requests);
        binding.rvJoinRequests.setLayoutManager(new LinearLayoutManager(this));
        binding.rvJoinRequests.setAdapter(joinRequestsRVA);
        presenter.onCreate();
    }

    @Override
    public void addJoinRequest(JoinRequest joinRequest) {
        joinRequestsRVA.addJoinRequest(joinRequest);
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
            DaggerViewInjectorComponent.builder()
                    .serviceComponent(DaggerServiceComponent.create())
                    .build().inject(this);
        }
        return presenter;
    }

    @Override
    public void onApproval(JoinRequest joinRequest, boolean approved) {
        presenter.joinRequestDecision(joinRequest, approved);
    }
}
