package com.wickeddevs.easywars.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.miscellaneous.HideProgressBarRequestListener;

/**
 * Created by 375csptssce on 6/9/16.
 */
public class ClanAdapter extends RecyclerView.Adapter<ClanAdapter.ClanViewHolder> {

    private Context context;
    private ArrayList<ApiClan> apiClans;
    private View.OnClickListener listener;

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
    public void onBindViewHolder(final ClanViewHolder holder, int position) {
        ApiClan apiClan = apiClans.get(position);

        holder.name.setText(apiClan.name);
        holder.members.setText("Members " + apiClan.members + "/50");
        holder.tag.setText(apiClan.tag);
        holder.progressBar.setVisibility(View.VISIBLE);
        Glide.with(context).load(apiClan.badgeUrls.medium)
                .listener(new HideProgressBarRequestListener(holder.progressBar))
                .centerCrop()
                .into(holder.badge);
    }

    @Override
    public int getItemCount() {
        return apiClans.size();
    }

    public void addClan(ApiClan apiClan) {
        apiClans.add(apiClan);
        notifyDataSetChanged();
        //notifyItemInserted(apiClans.size());
    }

    public void setClans(ArrayList<ApiClan> apiClans) {
        this.apiClans = apiClans;
        notifyDataSetChanged();
    }

    public void clearClans() {
        this.apiClans.clear();
        notifyDataSetChanged();
    }
    public ApiClan getClan(int position) {
        return apiClans.get(position);
    }

    public static class ClanViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView members;
        TextView tag;
        ProgressBar progressBar;
        ImageView badge;

        ClanViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvSearchName);
            members = (TextView) itemView.findViewById(R.id.tvSearchMembers);
            tag = (TextView) itemView.findViewById(R.id.tvSearchTag);
            progressBar = (ProgressBar) itemView.findViewById(R.id.badgeProgressBar);
            badge = (ImageView) itemView.findViewById(R.id.ivBadge);
        }
    }
}
