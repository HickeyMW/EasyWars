package wickeddevs.easywars.noclan;

import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.model.JoinRequest;
import wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by 375csptssce on 7/28/16.
 */
public interface NoClanContract {

    interface View {

        void showNoClanUi();

        void showHomeUi();

        void showCreateClanUi();

        void showJoinClanUi();

        void showAwaitingCreateUi(CreateRequest createRequest, ApiClan apiClan);

        void showAwaitingJoinUi(ApiClan apiClan);

        void displayCreateNotVerified();

        void displayJoinDenied();
    }

    interface ViewListener {

        void onStart();

        void pressedCreateClan();

        void createClan(String username, String tag);

        void pressedJoinClan();

        void joinClan(String username, String tag, String message);

        void verifyCreateClan();

        void cancelCreateClan();

        void cancelJoinClan();
    }
}
