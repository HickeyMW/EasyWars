package wickeddevs.easywars.ui.startwar.warorder;

import wickeddevs.easywars.base.PView;
import wickeddevs.easywars.base.Presenter;
import wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by 375csptssce on 8/22/16.
 */
public interface WarOrderContract {

    interface View extends PView {

        void displayApiClan(ApiClan apiClan);

        String getClanTag();
    }

    interface ViewListener extends Presenter<WarOrderContract.View> {




    }
}
