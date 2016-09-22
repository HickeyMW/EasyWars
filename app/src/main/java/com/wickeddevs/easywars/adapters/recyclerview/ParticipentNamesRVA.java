package com.wickeddevs.easywars.adapters.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.data.model.war.Participent;

import java.util.ArrayList;

/**
 * Created by 375csptssce on 9/22/16.
 */

public class ParticipentNamesRVA extends RecyclerView.Adapter<ParticipentNamesRVA.ParticipentVH> {

    ArrayList<Participent> participents;
    View.OnClickListener listener;

    public ParticipentNamesRVA(ArrayList<Participent> participents, View.OnClickListener listener) {
        this.participents = participents;
        this.listener = listener;
    }

    @Override
    public ParticipentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        v.setOnClickListener(listener);
        return new ParticipentVH(v);
    }


    @Override
    public void onBindViewHolder(ParticipentNamesRVA.ParticipentVH holder, int position) {
        Participent participent = participents.get(position);
        if (participent.uid == null) {
            holder.name.setText(participent.name + "*");
        } else {
            holder.name.setText(participent.name);
        }
    }

    @Override
    public int getItemCount() {
        return participents.size();
    }


    public static class ParticipentVH extends RecyclerView.ViewHolder {
        TextView name;

        ParticipentVH(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvItemMemberName);
        }
    }
}
