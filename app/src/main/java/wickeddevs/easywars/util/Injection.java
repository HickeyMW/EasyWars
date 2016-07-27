package wickeddevs.easywars.util;

import wickeddevs.easywars.data.Services;
import wickeddevs.easywars.data.firebase.FbChatService;
import wickeddevs.easywars.data.firebase.FbClanService;
import wickeddevs.easywars.data.firebase.FbUserService;

/**
 * Created by 375csptssce on 7/26/16.
 */
public class Injection {

    private static Services.UserService sUserService = null;
    private static Services.ChatService sChatService = null;
    private static Services.ClanService sClanService = null;

    public static Services.UserService userService() {
        if (sUserService == null) {
            sUserService = new FbUserService();
        }
        return sUserService;
    }

    public static Services.ChatService chatService() {
        if (sChatService == null) {
            sChatService = new FbChatService();
        }
        return sChatService;
    }

    public static Services.ClanService clanService() {
        if (sClanService == null) {
            sClanService = new FbClanService();
        }
        return sClanService;
    }
}
