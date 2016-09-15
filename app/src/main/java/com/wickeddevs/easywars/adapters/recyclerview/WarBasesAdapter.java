package com.wickeddevs.easywars.adapters.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.util.Shared;

/**
 * Created by 375csptssce on 7/19/16.
 */
public class WarBasesAdapter extends RecyclerView.Adapter<WarBasesAdapter.WarBasesHolder> {

    private ArrayList<Base> bases;
    private View.OnClickListener listener;

    public WarBasesAdapter(ArrayList<Base> bases, View.OnClickListener listener) {
        this.bases = bases;
        this.listener = listener;
    }

    @Override
    public WarBasesAdapter.WarBasesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_war_base, parent, false);
        v.setOnClickListener(listener);
        return new WarBasesHolder(v);
    }

    @Override
    public void onBindViewHolder(WarBasesAdapter.WarBasesHolder holder, int position) {
        Base base = bases.get(position);
        holder.tvNumber.setText((position + 1) + ". ");
        holder.tvName.setText(base.name);
        holder.imageView.setImageResource(Shared.getThResource(base.thLevel));
        holder.tvClaimsAttacks.setText("Claims: " + base.claims.size() + "    Attacks: " + base.attacks.size());
        holder.ivStar1.setImageResource(R.drawable.empty_star);
        holder.ivStar2.setImageResource(R.drawable.empty_star);
        holder.ivStar3.setImageResource(R.drawable.empty_star);
        switch (base.stars) {
            case 3:
                holder.ivStar3.setImageResource(R.drawable.gold_star);
            case 2:
                holder.ivStar2.setImageResource(R.drawable.gold_star);
            case 1:
                holder.ivStar1.setImageResource(R.drawable.gold_star);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return bases.size();
    }

    public static class WarBasesHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView ivStar1;
        ImageView ivStar2;
        ImageView ivStar3;
        TextView tvNumber;
        TextView tvName;
        TextView tvClaimsAttacks;

        public WarBasesHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ivTownHall);
            ivStar1 = (ImageView) itemView.findViewById(R.id.tvAttack1Star1);
            ivStar2 = (ImageView) itemView.findViewById(R.id.tvAttack1Star2);
            ivStar3 = (ImageView) itemView.findViewById(R.id.tvAttack1Star3);
            tvNumber = (TextView) itemView.findViewById(R.id.tvOrderNumber);
            tvName = (TextView) itemView.findViewById(R.id.tvItemWarBaseName);
            tvClaimsAttacks = (TextView) itemView.findViewById(R.id.tvItemWarBaseClaimsAttacks);
        }
    }
}
