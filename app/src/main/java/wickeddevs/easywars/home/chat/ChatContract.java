package wickeddevs.easywars.home.chat;

import java.util.ArrayList;

import wickeddevs.easywars.data.model.Message;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public interface ChatContract {

    interface View {

        void setMessages(ArrayList<Message> messages);

        void addMessage(Message message);

        void clearSendText();
    }

    interface ViewListener {

        void start();

        void stop();

        void sendMessage(String body);
    }

}
