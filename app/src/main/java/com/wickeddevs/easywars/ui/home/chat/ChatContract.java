package com.wickeddevs.easywars.ui.home.chat;

import java.util.ArrayList;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.Message;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public interface ChatContract {

    interface View extends PView {

        void addMessage(Message message);

        void clearSendText();

        boolean isAdminChat();

        void toggleLoading(boolean loading);
    }

    interface ViewListener extends Presenter<ChatContract.View> {

        void onCreate();

        void onDestroy();

        void sendMessage(String body);
    }

}
