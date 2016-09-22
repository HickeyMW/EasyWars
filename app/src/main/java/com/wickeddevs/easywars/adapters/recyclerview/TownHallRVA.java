package com.wickeddevs.easywars.adapters.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.util.Shared;

/**
 * Created by 375csptssce on 7/25/16.
 */
public class TownHallRVA extends RecyclerView.Adapter<TownHallRVA.TownHallHolder> {

    View.OnClickListener listener;

    public TownHallRVA(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public TownHallRVA.TownHallHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_town_hall, parent, false);
        v.setOnClickListener(listener);
        return new TownHallHolder(v);
    }

    @Override
    public void onBindViewHolder(TownHallRVA.TownHallHolder holder, int position) {
        int thLevel = position + 1;
        holder.name.setText("Town Hall " + String.valueOf(thLevel));
        holder.imageView.setImageResource(Shared.getThResource(thLevel));

    }

    @Override
    public int getItemCount() {
        return 11;
    }

    public static class TownHallHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;

        public TownHallHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvThName);
            imageView = (ImageView) itemView.findViewById(R.id.ivThImage);
        }
    }
}
