package wickeddevs.easywars.ui.noclan.verifycreate;

import wickeddevs.easywars.base.PView;
import wickeddevs.easywars.base.Presenter;
import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by hicke_000 on 8/3/2016.
 */
public interface VerifyCreateClanContract {

    interface View extends PView {

        void displayCreateRequestDetails(CreateRequest createRequest, ApiClan apiClan);

        void navigateToHomeUi();

        void navigateToNoClanUi();
    }

    interface ViewListener extends Presenter<VerifyCreateClanContract.View> {

        void verifyCreateClan();

        void cancelCreateClan();

    }
}
