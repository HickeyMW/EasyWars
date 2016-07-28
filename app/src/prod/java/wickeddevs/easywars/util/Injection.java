package wickeddevs.easywars.util;

import wickeddevs.easywars.data.service.contract.ChatService;
import wickeddevs.easywars.data.service.contract.ClanService;
import wickeddevs.easywars.data.service.contract.UserService;
import wickeddevs.easywars.data.service.firebase.FbChatService;
import wickeddevs.easywars.data.service.firebase.FbClanService;
import wickeddevs.easywars.data.service.firebase.FbUserService;

/**
 * Created by 375csptssce on 7/26/16.
 */
public class Injection {

    private static UserService sUserService = null;
    private static ChatService sChatService = null;
    private static ClanService sClanService = null;

    public static UserService userService() {
        if (sUserService == null) {
            sUserService = new FbUserService();
        }
        return sUserService;
    }

    public static ChatService chatService() {
        if (sChatService == null) {
            sChatService = new FbChatService();
        }
        return sChatService;
    }

    public static ClanService clanService() {
        if (sClanService == null) {
            sClanService = new FbClanService();
        }
        return sClanService;
    }
}
