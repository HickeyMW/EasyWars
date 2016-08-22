package wickeddevs.easywars.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import wickeddevs.easywars.R;
import wickeddevs.easywars.data.model.war.Comment;

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
        if (position < claims.size()) {
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

        if (position < claims.size()) {
            String claim = claims.get(position);
            ClaimViewHolder vHolder = (ClaimViewHolder) holder;
            vHolder.name.setText(claim);
        } else {
            Comment comment = comments.get(position-claims.size());
            CommentViewHolder vHolder = (CommentViewHolder) holder;
            vHolder.name.setText(comment.name);
            vHolder.body.setText(comment.body);
            vHolder.dateTime.setText(comment.dateTime);
        }
    }

    @Override
    public int getItemCount() {
        return claims.size() + comments.size();
    }

    public void addClaim(String name) {

    }

    public void addComment(String name) {

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
