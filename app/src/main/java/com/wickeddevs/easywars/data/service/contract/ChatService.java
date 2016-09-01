package com.wickeddevs.easywars.data.service.contract;

import java.util.ArrayList;

import com.wickeddevs.easywars.data.model.Message;

/**
 * Created by 375csptssce on 7/28/16.
 */
public interface ChatService {

    interface MessageListener {

        void initialLoadComplete();

        void newMessage(Message message);
    }

    void setMessageListener(boolean isAdmin, MessageListener listener);

    void removeMessageListener();

    void sendMessage(boolean isAdmin, String body);
}
