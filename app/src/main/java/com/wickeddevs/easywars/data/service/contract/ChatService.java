package com.wickeddevs.easywars.data.service.contract;

import java.util.ArrayList;

import com.wickeddevs.easywars.data.model.Message;

/**
 * Created by 375csptssce on 7/28/16.
 */
public interface ChatService {

    interface MessageListener {

        void initialMessages(ArrayList<Message> messages);

        void newMessage(Message message);
    }

    void setMessageListener(MessageListener listener);

    void removeMessageListener();

    void sendMessage(String body);
}
