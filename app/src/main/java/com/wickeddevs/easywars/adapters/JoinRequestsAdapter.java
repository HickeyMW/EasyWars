package com.wickeddevs.easywars.adapters;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.data.model.JoinRequest;

/**
 * Created by 375csptssce on 8/15/16.
 */
public class JoinRequestsAdapter extends RecyclerView.Adapter<JoinRequestsAdapter.JoinRequestsViewHolder> {

    private ArrayList<JoinRequest> joinRequests;
    private ApprovalListener approvalListener;

    public JoinRequestsAdapter(ArrayList<JoinRequest> joinRequests, ApprovalListener approvalListener) {
        this.joinRequests = joinRequests;
        this.approvalListener = approvalListener;
    }

    @Override
    public JoinRequestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_join_request, parent, false);
        return new JoinRequestsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(JoinRequestsViewHolder holder, int position) {

        holder.name.setText(joinRequests.get(position).name);
        if (joinRequests.get(position).message.isEmpty()){
            holder.message.setVisibility(View.GONE);
        } else {
            holder.message.setText(joinRequests.get(position).message);
        }
        holder.joinRequest = joinRequests.get(position);
    }

    @Override
    public int getItemCount() {
        return joinRequests.size();
    }

    public JoinRequest getMember(int position) {
        return joinRequests.get(position);
    }

    public class JoinRequestsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView message;
        TextView status;
        Button btnAccept;
        Button btnDeny;
        JoinRequest joinRequest;
        View view;

        JoinRequestsViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = (TextView) itemView.findViewById(R.id.tvName);
            message = (TextView) itemView.findViewById(R.id.tvMessage);
            status = (TextView) itemView.findViewById(R.id.tvStatus);
            btnAccept = (Button) itemView.findViewById(R.id.btnAccept);
            btnDeny = (Button) itemView.findViewById(R.id.btnDeny);
            btnAccept.setOnClickListener(this);
            btnDeny.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String uid = joinRequest.id;
            btnAccept.setVisibility(View.INVISIBLE);
            btnDeny.setVisibility(View.INVISIBLE);
            status.setVisibility(View.VISIBLE);
            if (v.getId() == btnAccept.getId()) {
                JoinRequestsAdapter.this.approvalListener.onApproval(joinRequest, true);
                view.setBackgroundColor(Resources.getSystem().getColor(android.R.color.holo_green_light));
                status.setText("Accepted");
            } else {
                JoinRequestsAdapter.this.approvalListener.onApproval(joinRequest, false);
                view.setBackgroundColor(Resources.getSystem().getColor(android.R.color.holo_red_light));
                status.setText("Denied");
            }
        }
    }

    public interface ApprovalListener {
        void onApproval(JoinRequest joinRequest, boolean approved);
    }
}
