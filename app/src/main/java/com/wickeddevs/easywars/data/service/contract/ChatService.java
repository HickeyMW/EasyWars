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

    void setMemberMessageListener(MessageListener listener);

    void removeMemberMessageListener();

    void sendMemberMessage(String body);

    void setAdminMessageListener(MessageListener listener);

    void removeAdminMessageListener();

    void sendAdminMessage(String body);
}
