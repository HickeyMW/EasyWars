package com.wickeddevs.easywars.data.service.contract;

import com.wickeddevs.easywars.data.model.User;

/**
 * Created by hicke_000 on 8/2/2016.
 */
public interface UserService {

    boolean isLoggedIn();

    void logout();

    void getUser(LoadUserCallback callback);

    void listenOnUser(LoadUserCallback callback);

    interface LoadUserCallback {
        void onUserLoaded(User user);
    }

    boolean isMyId(String uid);
}
