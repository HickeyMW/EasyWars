package wickeddevs.easywars.data;

import wickeddevs.easywars.Presenter;

/**
 * Created by 375csptssce on 7/26/16.
 */
public interface Services {

    interface User {

        interface LoggedInCallback {
            void response(boolean isLoggedIn);
        }

        void isLoggedIn(LoggedInCallback callback);

        String getUid();
    }
}
