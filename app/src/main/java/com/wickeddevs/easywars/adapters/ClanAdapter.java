package com.wickeddevs.easywars.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by 375csptssce on 6/9/16.
 */
public class ClanAdapter extends RecyclerView.Adapter<ClanAdapter.ClanViewHolder> {

    private Context context;
    private ArrayList<ApiClan> apiClans;
    private View.OnClickListener listener;


    public ClanAdapter(ArrayList<ApiClan> apiClans, Context context, View.OnClickListener listener) {
        this.apiClans = apiClans;
        this.context = context;
        this.listener = listener;
    }

    public ClanAdapter(Context context, View.OnClickListener listener) {
        this.apiClans = new ArrayList<>();
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ClanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clan, parent, false);
        v.setOnClickListener(listener);
        return new ClanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ClanViewHolder holder, int position) {
        ApiClan apiClan = apiClans.get(position);

        holder.mName.setText(apiClan.name);
        holder.mMembers.setText("Members " + apiClan.members + "/50");
        holder.mTag.setText(apiClan.tag);
        Glide.with(context).load(apiClan.badgeUrls.medium).centerCrop().into(holder.mBadge);
    }

    @Override
    public int getItemCount() {
        return apiClans.size();
    }

    public void addClan(ApiClan apiClan) {
        apiClans.add(apiClan);

        notifyItemInserted(apiClans.size() - 1);
    }

    public ApiClan getClan(int position) {
        return apiClans.get(position);
    }

    public static class ClanViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        TextView mMembers;
        TextView mTag;
        ImageView mBadge;

        ClanViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.tvSearchName);
            mMembers = (TextView) itemView.findViewById(R.id.tvSearchMembers);
            mTag = (TextView) itemView.findViewById(R.id.tvSearchTag);
            mBadge = (ImageView) itemView.findViewById(R.id.ivBadge);
        }
    }
}
