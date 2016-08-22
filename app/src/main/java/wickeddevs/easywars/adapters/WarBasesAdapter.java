package wickeddevs.easywars.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import wickeddevs.easywars.R;
import wickeddevs.easywars.data.model.war.Base;
import wickeddevs.easywars.util.Shared;

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
        holder.tvName.setText(String.valueOf(position) + ". " + base.name);
        holder.imageView.setImageResource(Shared.getThResource(base.townHallLevel));
    }

    @Override
    public int getItemCount() {
        return bases.size();
    }

    public static class WarBasesHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName;
        TextView tvClaimed;

        public WarBasesHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ivItemWar);
            tvName = (TextView) itemView.findViewById(R.id.tvItemWarName);
            tvClaimed = (TextView) itemView.findViewById(R.id.tvItemWarClaimed);
        }
    }
}
