package wickeddevs.easywars.data;

import wickeddevs.easywars.data.model.Member;
import wickeddevs.easywars.data.model.Message;
import wickeddevs.easywars.data.model.User;

/**
 * Created by 375csptssce on 7/26/16.
 */
public interface Services {

    interface UserService {

        interface LoadUserCallback {

            void onUserLoaded(User user);
        }

        boolean isLoggedIn();

        void getUser(LoadUserCallback callback);
    }

    interface ChatService {

        interface MessageListener {

            void newMessage(Message message);
        }

        void setMessageListener(MessageListener listener);

        void removeMessageListener();

        void sendMessage(String body);
    }

    interface ClanService {

        interface LoadMemberCallback {

            void onMemberLoaded(Member member, boolean isUser);
        }

        void getMember(String uid, LoadMemberCallback callback);
    }
}
