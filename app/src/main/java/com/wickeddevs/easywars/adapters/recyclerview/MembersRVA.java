package com.wickeddevs.easywars.adapters.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.data.model.Member;

import java.util.ArrayList;

/**
 * Created by 375csptssce on 9/23/16.
 */

public class MembersRVA extends RecyclerView.Adapter<MembersRVA.MembersVH> {

    ArrayList<Member> members;
    View.OnClickListener listener;

    public MembersRVA(ArrayList<Member> members, View.OnClickListener listener) {
        this.members = members;
        this.listener = listener;
    }

    @Override
    public MembersRVA.MembersVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_town_hall, parent, false);
        v.setOnClickListener(listener);
        return new MembersVH(v);
    }

    @Override
    public void onBindViewHolder(MembersRVA.MembersVH holder, int position) {
        holder.name.setText(members.get(position).name);
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public Member getMember(int position) {
        return members.get(position);
    }


    public static class MembersVH extends RecyclerView.ViewHolder {
        TextView name;

        public MembersVH(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvThName);
        }
    }
}
