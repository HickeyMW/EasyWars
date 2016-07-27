package wickeddevs.easywars.home.chat;

import wickeddevs.easywars.Presenter;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public interface ChatContract {

    interface View {

        void addMessage(String name, String body, String dateTime, boolean sentMessage);

        void clearSendText();
    }

    interface ViewListener extends Presenter {

        void sendMessage(String message);
    }

}
