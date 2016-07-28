package wickeddevs.easywars.home.chat;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import java.util.ArrayList;

import wickeddevs.easywars.R;
import wickeddevs.easywars.adapters.ChatAdapter;
import wickeddevs.easywars.data.model.Message;
import wickeddevs.easywars.databinding.FragmentChatBinding;
import wickeddevs.easywars.util.Injection;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements ChatContract.View {

    private FragmentChatBinding mBinding;
    private ChatContract.ViewListener mPresenter;
    private ChatAdapter mChatAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ChatPresenter(this, Injection.chatService(), Injection.clanService());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);
        mBinding.rvMessages.setLayoutManager(mLinearLayoutManager);
        mBinding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.sendMessage(mBinding.etChatInput.getText().toString());
            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.stop();
    }

    @Override
    public void setMessages(ArrayList<Message> messages) {
        mChatAdapter = new ChatAdapter(messages);
        mChatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mChatAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 || lastVisiblePosition == friendlyMessageCount -2) {
                    mBinding.rvMessages.scrollToPosition(positionStart);
                }
            }
        });
        mBinding.rvMessages.setAdapter(mChatAdapter);
    }

    @Override
    public void addMessage(Message message) {

        mChatAdapter.addMessage(message);
    }

    @Override
    public void clearSendText() {
        mBinding.etChatInput.setText("");
    }
}
