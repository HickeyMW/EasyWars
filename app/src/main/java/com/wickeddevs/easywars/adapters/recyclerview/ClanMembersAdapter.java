package com.wickeddevs.easywars.adapters.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.wickeddevs.easywars.R;

/**
 * Created by 375csptssce on 6/21/16.
 */
public class ClanMembersAdapter extends RecyclerView.Adapter<ClanMembersAdapter.ClanMemberViewHolder> {

    private ArrayList <String> members;
    private View.OnClickListener listener;
    private ArrayList <String> removedmembers = new ArrayList<>();
    private ArrayList <Integer> positions = new ArrayList<>();


    public ClanMembersAdapter(ArrayList<String> members, View.OnClickListener listener) {
        this.members = members;
        this.listener = listener;
    }

    @Override
    public ClanMemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        v.setOnClickListener(listener);
        return new ClanMemberViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ClanMemberViewHolder holder, int position) {

        holder.name.setText(members.get(position));
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public void remove(int position) {
        removedmembers.add(members.get(position));
        positions.add(position);
        members.remove(position);
        notifyItemRemoved(position);
    }

    public void undo() {
        int position = removedmembers.size() - 1;
        members.add(removedmembers.get(position));
        members.add(positions.get(position), removedmembers.get(position));
        notifyItemInserted(positions.get(position));
        removedmembers.remove(position);
        positions.remove(position);
    }

    public String getMember(int position) {
        return members.get(position);
    }

    public static class ClanMemberViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        ClanMemberViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvItemMemberName);
        }
    }
}
