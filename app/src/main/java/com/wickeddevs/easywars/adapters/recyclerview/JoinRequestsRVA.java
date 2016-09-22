package com.wickeddevs.easywars.adapters.recyclerview;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.data.model.JoinRequest;
import com.wickeddevs.easywars.util.Shared;

/**
 * Created by 375csptssce on 8/15/16.
 */
public class JoinRequestsRVA extends RecyclerView.Adapter<JoinRequestsRVA.JoinRequestsViewHolder> {

    private ArrayList<JoinRequest> joinRequests = new ArrayList<>();
    private ApprovalListener approvalListener;

    public JoinRequestsRVA(ApprovalListener approvalListener) {
        this.approvalListener = approvalListener;
    }

    @Override
    public JoinRequestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_join_request, parent, false);
        return new JoinRequestsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(JoinRequestsViewHolder holder, int position) {
        JoinRequest joinRequest = joinRequests.get(position);
        holder.name.setText("Name: " + joinRequest.name);
        if (joinRequest.message.isEmpty()){
            holder.message.setVisibility(View.GONE);
        } else {
            holder.message.setText("Message: " + joinRequest.message);
        }
        holder.joinRequest = joinRequests.get(position);
        holder.ivTownHall.setImageResource(Shared.getThResource(joinRequest.thLevel));
    }

    @Override
    public int getItemCount() {
        return joinRequests.size();
    }

    public void addJoinRequest(JoinRequest joinRequest) {
        joinRequests.add(joinRequest);
        notifyItemInserted(joinRequests.size());
    }

    public class JoinRequestsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivTownHall;
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
            ivTownHall = (ImageView) itemView.findViewById(R.id.ivTownHall) ;
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
            String uid = joinRequest.key;
            btnAccept.setVisibility(View.INVISIBLE);
            btnDeny.setVisibility(View.INVISIBLE);
            status.setVisibility(View.VISIBLE);
            if (v.getId() == btnAccept.getId()) {
                JoinRequestsRVA.this.approvalListener.onApproval(joinRequest, true);
                status.setTextColor(Resources.getSystem().getColor(android.R.color.holo_green_light));
                status.setText("Accepted");
            } else {
                JoinRequestsRVA.this.approvalListener.onApproval(joinRequest, false);
                status.setTextColor(Resources.getSystem().getColor(android.R.color.holo_red_light));
                status.setText("Denied");
            }
        }
    }

    public interface ApprovalListener {
        void onApproval(JoinRequest joinRequest, boolean approved);
    }
}
