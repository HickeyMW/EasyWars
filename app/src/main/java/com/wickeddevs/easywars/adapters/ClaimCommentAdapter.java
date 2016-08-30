package com.wickeddevs.easywars.adapters;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.data.model.war.Comment;

/**
 * Created by 375csptssce on 8/22/16.
 */
public class ClaimCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> claims;
    private ArrayList<Comment> comments;

    public ClaimCommentAdapter(ArrayList<String> claims, ArrayList<Comment> comments) {
        this.claims = claims;
        this.comments = comments;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_claim, parent, false);
            return new ClaimViewHolder(v);
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position == 0) {
            //String claim = claims.get(position);
            ClaimViewHolder vHolder = (ClaimViewHolder) holder;
            String claimText = "Claim Order";
            for (int i = 0; i < claims.size(); i++) {
                claimText += "\n" + String.valueOf(i + 1) + ". " + claims.get(i);
            }
            vHolder.name.setText(claimText);
        } else {

            Comment comment = comments.get(position-1);
            CommentViewHolder vHolder = (CommentViewHolder) holder;
            vHolder.name.setText(comment.name);
            vHolder.body.setText(comment.body);
            vHolder.dateTime.setText(comment.dateTime);
        }
    }

    @Override
    public int getItemCount() {
        return comments.size() + 1;
    }

    public void addClaim(String name) {
        claims.add(name);
        notifyItemChanged(0);
    }

    public void removeClaim(String name) {
        for (int i = 0; i < claims.size(); i++) {
            if (claims.get(i).equals(name)) {
                claims.remove(i);
                notifyItemChanged(0);
                //notifyItemRemoved(i);
//                if (claims.size() > 0) {
//                    notifyItemRangeChanged(0, claims.size());
//                }
            }
        }
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        notifyItemInserted(comments.size());
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView body;
        TextView dateTime;

        public CommentViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.item_comment_name);
            body = (TextView)itemView.findViewById(R.id.item_comment_body);
            dateTime = (TextView)itemView.findViewById(R.id.item_comment_timestamp);
        }
    }

    public class ClaimViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ClaimViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.item_claim_name);
        }
    }
}