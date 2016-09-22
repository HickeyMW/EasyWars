package com.wickeddevs.easywars.adapters.recyclerview;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.data.model.war.Attack;
import com.wickeddevs.easywars.data.model.war.Participent;
import com.wickeddevs.easywars.databinding.ItemClanOverviewBinding;
import com.wickeddevs.easywars.util.Shared;

import java.util.ArrayList;

/**
 * Created by 375csptssce on 9/9/16.
 */
public class ParticipentAttacksAdapter extends RecyclerView.Adapter<ParticipentAttacksAdapter.ParticipentHolder> {

    ArrayList<Participent> participents;
    private View.OnClickListener listener;

    public ParticipentAttacksAdapter(ArrayList<Participent> participents, View.OnClickListener listener) {
        this.participents = participents;
        this.listener = listener;
    }

    @Override
    public ParticipentAttacksAdapter.ParticipentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clan_overview, parent, false);
        v.setOnClickListener(listener);
        return new ParticipentHolder(v);
    }

    @Override
    public void onBindViewHolder(ParticipentAttacksAdapter.ParticipentHolder holder, int position) {
        Participent participent = participents.get(position);
        holder.binding.ivTownHall.setImageResource(Shared.getThResource(position + 1));
        holder.binding.tvOrderNumber.setText((position + 1) + ".");
        holder.binding.tvName.setText(participent.name);
        if (participent.attackClaims.size() == 0) {
            holder.binding.tvAttack1.setText("No claim or attack");
            holder.binding.tvAttack2.setText("No claim or attack");
        } else if (participent.attackClaims.size() == 1) {
            Attack attack = participent.attackClaims.get(0);
            holder.binding.tvAttack1.setText(formatAttackStatus(attack, true, holder.binding));
            holder.binding.tvAttack2.setText("No claim or attack");
        } else {
            Attack attack1 = participent.attackClaims.get(0);
            Attack attack2 = participent.attackClaims.get(1);
            holder.binding.tvAttack1.setText(formatAttackStatus(attack1, true, holder.binding));
            holder.binding.tvAttack2.setText(formatAttackStatus(attack2, false , holder.binding));
        }
    }

    private String formatAttackStatus(Attack attack, boolean isAttack1, ItemClanOverviewBinding binding) {
        if (attack.stars == -1) {
            return "Claim on #" + (attack.base + 1) + " " + attack.baseName;
        }
        if (isAttack1) {
            binding.layoutAttack1Stars.setVisibility(View.VISIBLE);
            switch (attack.stars) {
                case 3:
                    binding.ivAttack1Star3.setImageResource(R.drawable.gold_star);
                case 2:
                    binding.ivAttack1Star2.setImageResource(R.drawable.gold_star);
                case 1:
                    binding.ivAttack1Star1.setImageResource(R.drawable.gold_star);
            }
        } else {
            binding.layoutAttack2Stars.setVisibility(View.VISIBLE);
            switch (attack.stars) {
                case 3:
                    binding.ivAttack2Star3.setImageResource(R.drawable.gold_star);
                case 2:
                    binding.ivAttack2Star2.setImageResource(R.drawable.gold_star);
                case 1:
                    binding.ivAttack2Star1.setImageResource(R.drawable.gold_star);
            }
        }

        return "Attack on #" + (attack.base + 1) + " " + attack.baseName;
    }

    @Override
    public int getItemCount() {
        return participents.size();
    }

    public static class ParticipentHolder extends RecyclerView.ViewHolder {
        ItemClanOverviewBinding binding;

        public ParticipentHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
