package wickeddevs.easywars.ui.noclan.join;

import java.util.ArrayList;

import wickeddevs.easywars.base.PView;
import wickeddevs.easywars.base.Presenter;
import wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by hicke_000 on 8/3/2016.
 */
public interface JoinClanContract {

    interface View extends PView {

        void allowJoin();

        String getClanTag();

        void displayClanInfo(ApiClan apiClan);

        void navigateToVerifyJoinClanUi();
    }

    interface ViewListener extends Presenter<JoinClanContract.View> {

        void selectedName(String name);

        void requestJoin(String message);
    }
}
