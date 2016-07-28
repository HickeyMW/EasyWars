package wickeddevs.easywars.data.service.contract;

import wickeddevs.easywars.data.model.User;

/**
 * Created by 375csptssce on 7/28/16.
 */
public interface UserService {

    interface LoadUserCallback {

        void onUserLoaded(User user);
    }

    boolean isLoggedIn();

    void getUser(LoadUserCallback callback);

    void setUser(User user);

    void setState(int state);
}
