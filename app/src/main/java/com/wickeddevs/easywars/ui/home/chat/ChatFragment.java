package com.wickeddevs.easywars.ui.home.chat;


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

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.ChatAdapter;
import com.wickeddevs.easywars.adapters.SpaceItemDecoration;
import com.wickeddevs.easywars.base.BasePresenterFragment;
import com.wickeddevs.easywars.dagger.Injector;
import com.wickeddevs.easywars.data.model.Message;
import com.wickeddevs.easywars.databinding.FragmentChatBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends BasePresenterFragment<ChatContract.ViewListener> implements ChatContract.View {

    private static final String IS_ADMIN = "IS_ADMIN";

    @Inject
    public ChatContract.ViewListener presenter;

    private FragmentChatBinding binding;
    private ChatAdapter chatAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        binding.rvMessages.setLayoutManager(linearLayoutManager);
        binding.rvMessages.addItemDecoration(new SpaceItemDecoration());
        chatAdapter = new ChatAdapter();
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
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.sendMessage(binding.etChatInput.getText().toString());
            }
        });
        presenter.onCreate();
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public boolean isAdminChat() {
        return getArguments().getBoolean(IS_ADMIN);
    }

    @Override
    public void toggleLoading(boolean loading) {
        if (loading) {
            binding.layoutChat.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.layoutChat.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void addMessage(Message message) {
        chatAdapter.addMessage(message);
    }

    @Override
    public void clearSendText() {
        binding.etChatInput.setText("");
    }

    protected ChatContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
        }
        return presenter;
    }

    public static ChatFragment getInstance(boolean isAdmin) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_ADMIN, isAdmin);
        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(bundle);
        return chatFragment;
    }
}
