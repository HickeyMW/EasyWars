package wickeddevs.easywars.data.service.contract;

import wickeddevs.easywars.data.model.Clan;
import wickeddevs.easywars.data.model.Member;

/**
 * Created by 375csptssce on 7/28/16.
 */
public interface ClanService {

    interface LoadMemberCallback {

        void onMemberLoaded(Member member);
    }

    interface LoadClanCallback {

        void onClanLoaded(Clan clan);
    }

    interface AdminCheckCallback {

        void onLoaded(boolean isAdmin);
    }

    void getMember(String uid, LoadMemberCallback callback);

    void getClan(LoadClanCallback callback);

    void checkIfAdmin(AdminCheckCallback callback);
}
