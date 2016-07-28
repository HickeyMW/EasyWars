package wickeddevs.easywars.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import wickeddevs.easywars.R;
import wickeddevs.easywars.data.model.Message;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Message> mMessages;

    public ChatAdapter(ArrayList<Message> messages) {
        mMessages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        if (mMessages.get(position).isSentMessage) {
            return 0;
        }
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_send, parent, false);
            return new ChatSendViewHolder(v);
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_recieve, parent, false);
        return new ChatReceiveViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = mMessages.get(position);

        if (holder instanceof ChatSendViewHolder) {
            ChatSendViewHolder vHolder = (ChatSendViewHolder) holder;
            vHolder.name.setText(message.name);
            vHolder.body.setText(message.body);
            vHolder.dateTime.setText(message.dateTime);
        } else {
            ChatReceiveViewHolder vHolder = (ChatReceiveViewHolder) holder;
            vHolder.name.setText(message.name);
            vHolder.body.setText(message.body);
            vHolder.dateTime.setText(message.dateTime);
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public void addMessage(Message message) {
        mMessages.add(message);
        notifyItemInserted(mMessages.size() - 1);
    }

    public class ChatSendViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView body;
        TextView dateTime;

        public ChatSendViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.item_chat_send_name);
            body = (TextView)itemView.findViewById(R.id.item_chat_send_message);
            dateTime = (TextView)itemView.findViewById(R.id.item_chat_send_timestamp);
        }
    }

    public class ChatReceiveViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView body;
        TextView dateTime;

        public ChatReceiveViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.item_chat_recieve_name);
            body = (TextView)itemView.findViewById(R.id.item_chat_recieve_message);
            dateTime = (TextView)itemView.findViewById(R.id.item_chat_recieve_timestamp);
        }
    }
}
