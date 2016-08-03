package wickeddevs.easywars.ui.noclan;

import wickeddevs.easywars.base.PView;
import wickeddevs.easywars.base.Presenter;
import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by 375csptssce on 7/28/16.
 */
public interface NoClanContract {

    interface View extends PView {

        void showNoClanUi();

        void showHomeUi();

        void showCreateClanUi();

        void showJoinClanUi();

        void showAwaitingCreateUi(CreateRequest createRequest, ApiClan apiClan);

        void showAwaitingJoinUi(ApiClan apiClan);

        void displayCreateNotVerified();

        void displayJoinDenied();
    }

    interface ViewListener extends Presenter<NoClanContract.View> {

        void pressedCreateClan();

        void createClan(String username, String tag);

        void pressedJoinClan();

        void joinClan(String username, String tag, String message);

        void verifyCreateClan();

        void cancelCreateClan();

        void cancelJoinClan();
    }
}
