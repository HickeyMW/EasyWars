package wickeddevs.easywars.ui.noclan.create;

import java.util.ArrayList;

import wickeddevs.easywars.base.PView;
import wickeddevs.easywars.base.Presenter;
import wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public interface CreateClanContract {

    interface View extends PView {

        String getClanTag();

        void displayClanInfo(ApiClan apiClan);

        void allowCreate();

        void navigateToVerifyCreateClanUi();
    }

    interface ViewListener extends Presenter<CreateClanContract.View> {

        void selectedName(String name);

        void createClanRequest();
    }
}
