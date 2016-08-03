package wickeddevs.easywars.ui.home.chat;

import java.util.ArrayList;

import wickeddevs.easywars.base.PView;
import wickeddevs.easywars.base.Presenter;
import wickeddevs.easywars.data.model.Message;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public interface ChatContract {

    interface View extends PView {

        void setMessages(ArrayList<Message> messages);

        void addMessage(Message message);

        void clearSendText();
    }

    interface ViewListener extends Presenter<ChatContract.View> {

        void sendMessage(String body);
    }

}
