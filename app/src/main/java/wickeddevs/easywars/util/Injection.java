package wickeddevs.easywars.util;

import wickeddevs.easywars.data.Services;
import wickeddevs.easywars.data.firebase.UserService;

/**
 * Created by 375csptssce on 7/26/16.
 */
public class Injection {

    private static Services.User mUserService = null;

    public static Services.User provideUserService() {
        if (mUserService == null) {
            mUserService = new UserService();
        }
        return mUserService;
    }
}
