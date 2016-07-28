package wickeddevs.easywars.data;

import java.util.ArrayList;

import wickeddevs.easywars.data.model.Clan;
import wickeddevs.easywars.data.model.CreateRequest;
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

            void initialMessages(ArrayList<Message> messages);

            void newMessage(Message message);
        }

        void setMessageListener(MessageListener listener);

        void removeMessageListener();

        void sendMessage(String body);
    }

    interface ClanService {

        interface LoadMemberCallback {

            void onMemberLoaded(Member member);
        }

        interface LoadClanCallback {

            void onClanLoaded(Clan clan);
        }

        void getMember(String uid, LoadMemberCallback callback);

        void getClan(LoadClanCallback callback);
    }

    interface ApiService {

        interface LoadApiClanCallback {

            void onApiClanLoaded(Member member);
        }

        interface SearchApiClansCallback {

            void onApiClansLoaded(Clan clan);
        }

        void getApiClan(String tag, LoadApiClanCallback callback);

        void searchClans(SearchApiClansCallback callback);
    }

    interface CreateClanService {

        interface LoadCreateRequestCallback {

            void onCreateRequestLoaded(CreateRequest createRequest);
        }

        void getCreateRequest(LoadCreateRequestCallback callback);

        void setCreateRequest(CreateRequest createRequest);
    }

    interface JoinClanService {

        interface LoadCreateRequestCallback {

            void onCreateRequestLoaded(CreateRequest createRequest);
        }

        void getCreateRequest(LoadCreateRequestCallback callback);

        void setCreateRequest(CreateRequest createRequest);
    }
}
