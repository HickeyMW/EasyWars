package com.wickeddevs.easywars.adapters.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.model.api.ApiMember;
import com.wickeddevs.easywars.data.model.war.Participent;

import java.util.ArrayList;

/**
 * Created by 375csptssce on 9/12/16.
 */
public class ParticipentRVA extends RecyclerView.Adapter<ParticipentRVA.ParticipentViewHolder> {

    private ArrayList<Participent> participents = new ArrayList<>();
    private ArrayList<Participent> removedParticipents = new ArrayList<>();
    private ArrayList<Integer> positions = new ArrayList<>();
    private View.OnClickListener listener;


    public ParticipentRVA(ArrayList<Member> members, ArrayList<ApiMember> apiMembers, View.OnClickListener listener) {
        this.listener = listener;

        for (Member member : members) {
            participents.add(new Participent(member.uid, member.name, member.thLevel));
        }
        for (ApiMember apiMember : apiMembers) {
            boolean addMember = false;
            for (int i = 0; i < participents.size(); i++) {
                if (participents.get(i).name.equals(apiMember.name)) {
                    break;
                }
                if (i == participents.size() - 1) {
                    addMember = true;
                }
            }
            if (addMember) {
                participents.add(new Participent(apiMember.name));
            }
        }
    }

    @Override
    public ParticipentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        v.setOnClickListener(listener);
        return new ParticipentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ParticipentViewHolder holder, int position) {
        String name;
        Participent participent = participents.get(position);
        if (participent.uid == null) {
            name = participent.name + "*";
        } else {
            name = participent.name;
        }
        holder.name.setText(name);
    }

    @Override
    public int getItemCount() {
        return participents.size();
    }

    public void remove(int position) {
        removedParticipents.add(participents.get(position));
        positions.add(position);
        participents.remove(position);
        notifyItemRemoved(position);
    }

    public void undo() {
        int position = removedParticipents.size() - 1;
        participents.add(removedParticipents.get(position));
        participents.add(positions.get(position), removedParticipents.get(position));
        notifyItemInserted(positions.get(position));
        removedParticipents.remove(position);
        positions.remove(position);
    }

    public Participent getParticipent(int position) {
        return participents.get(position);
    }

    public static class ParticipentViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        ParticipentViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvItemMemberName);
        }
    }
}
