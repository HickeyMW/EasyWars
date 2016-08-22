package wickeddevs.easywars.ui.home.chat;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

import wickeddevs.easywars.R;
import wickeddevs.easywars.adapters.ChatAdapter;
import wickeddevs.easywars.base.BasePresenterFragment;
import wickeddevs.easywars.dagger.Injector;
import wickeddevs.easywars.data.model.Message;
import wickeddevs.easywars.databinding.FragmentChatBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends BasePresenterFragment<ChatContract.ViewListener> implements ChatContract.View {

    @Inject
    public ChatContract.ViewListener presenter;

    private FragmentChatBinding binding;
    private ChatAdapter chatAdapter;
    private LinearLayoutManager linearLayoutManager;

    protected ChatContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
        }
        return presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        binding.rvMessages.setLayoutManager(linearLayoutManager);
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.sendMessage(binding.etChatInput.getText().toString());
            }
        });
        return binding.getRoot();
    }

    @Override
    public void toggleProgressBar(boolean loading) {

    }

    @Override
    public void displayMessage(String message) {

    }

    @Override
    public void setMessages(ArrayList<Message> messages) {
        chatAdapter = new ChatAdapter(messages);
        chatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = chatAdapter.getItemCount();
                int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 || lastVisiblePosition == friendlyMessageCount -2) {
                    binding.rvMessages.scrollToPosition(positionStart);
                }
            }
        });
        binding.rvMessages.setAdapter(chatAdapter);
    }

    @Override
    public void addMessage(Message message) {

        chatAdapter.addMessage(message);
    }

    @Override
    public void clearSendText() {
        binding.etChatInput.setText("");
    }
}
