package wickeddevs.easywars.home.chat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wickeddevs.easywars.R;
import wickeddevs.easywars.databinding.FragmentChatBinding;
import wickeddevs.easywars.util.Injection;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements ChatContract.View {

    private FragmentChatBinding mBinding;
    private ChatContract.ViewListener mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ChatPresenter(this, Injection.chatService(), Injection.clanService());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
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
    public void addMessage(String name, String body, String dateTime, boolean sentMessage) {

    }

    @Override
    public void clearSendText() {

    }
}
