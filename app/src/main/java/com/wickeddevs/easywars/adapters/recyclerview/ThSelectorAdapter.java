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
 * Created by 375csptssce on 9/7/16.
 */
public class ThSelectorAdapter extends RecyclerView.Adapter<ThSelectorAdapter.ThSelectorViewHolder> {

    private View.OnClickListener listener;

    public ThSelectorAdapter(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ThSelectorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_townhall, parent, false);
        v.setOnClickListener(listener);
        return new ThSelectorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ThSelectorViewHolder holder, int position) {
        holder.imageView.setImageResource(Shared.getThResource(position + 1));
        holder.textView.setText("TH " + (position + 1));
    }

    @Override
    public int getItemCount() {
        return 11;
    }

    public static class ThSelectorViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ThSelectorViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ivThLevelSelector);
            textView = (TextView) itemView.findViewById(R.id.tvThLevelSelector);
        }
    }
}
