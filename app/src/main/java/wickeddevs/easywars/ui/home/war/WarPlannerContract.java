package wickeddevs.easywars.ui.home.war;

import java.util.ArrayList;

import wickeddevs.easywars.base.PView;
import wickeddevs.easywars.base.Presenter;
import wickeddevs.easywars.data.model.war.Base;

/**
 * Created by 375csptssce on 8/16/16.
 */
public interface WarPlannerContract {

    interface View extends PView {

        void displayWarBases(ArrayList<Base> bases);

        void displayNoCurrentWar(boolean isAdmin);

    }

    interface ViewListener extends Presenter<WarPlannerContract.View> {

    }
}
