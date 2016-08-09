package wickeddevs.easywars.data.service.contract;

import wickeddevs.easywars.data.model.User;

/**
 * Created by hicke_000 on 8/2/2016.
 */
public interface UserService {

    boolean isLoggedIn();

    void getUser(LoadUserCallback callback);

    void listenOnUser(LoadUserCallback callback);

    interface LoadUserCallback {
        void onUserLoaded(User user);
    }
}
