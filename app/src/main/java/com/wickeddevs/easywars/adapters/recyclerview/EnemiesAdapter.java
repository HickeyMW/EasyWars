package com.wickeddevs.easywars.adapters.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.util.Shared;

import java.util.ArrayList;

/**
 * Created by 375csptssce on 9/20/16.
 */

public class EnemiesAdapter extends RecyclerView.Adapter<EnemiesAdapter.EnemiesHolder> {

    View.OnClickListener listener;
    ArrayList<Base> bases;

    public EnemiesAdapter(ArrayList<Base> bases, View.OnClickListener listener) {
        this.bases = bases;
        this.listener = listener;
    }

    @Override
    public EnemiesAdapter.EnemiesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_town_hall, parent, false);
        v.setOnClickListener(listener);
        return new EnemiesHolder(v);
    }

    @Override
    public void onBindViewHolder(EnemiesAdapter.EnemiesHolder holder, int position) {
        int thLevel = position + 1;
        holder.name.setText("Town Hall " + String.valueOf(thLevel));
        holder.imageView.setImageResource(Shared.getThResource(thLevel));

    }

    @Override
    public int getItemCount() {
        return 11;
    }

    public static class EnemiesHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;

        public EnemiesHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvThName);
            imageView = (ImageView) itemView.findViewById(R.id.ivThImage);
        }
    }
}
