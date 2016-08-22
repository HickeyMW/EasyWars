package wickeddevs.easywars.ui.home;

import wickeddevs.easywars.base.PView;
import wickeddevs.easywars.base.Presenter;
import wickeddevs.easywars.data.model.Member;
import wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by 375csptssce on 8/16/16.
 */
public interface HomeContract {

    interface View extends PView {

        void navigateToNoClanUi();

        void displayUi(Member member, ApiClan apiClan);
    }

    interface ViewListener extends Presenter<HomeContract.View> {

    }
}
