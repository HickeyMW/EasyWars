package com.wickeddevs.easywars.ui.home.war;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.war.War;

/**
 * Created by 375csptssce on 8/16/16.
 */
public interface WarPlannerContract {

    interface View extends PView {

        void displayWar(War war, boolean isAdmin);

        void displayNoCurrentWar(boolean isAdmin);

        void toggleLoading(boolean loading);
    }

    interface ViewListener extends Presenter<WarPlannerContract.View> {

        void onCreate();

        void pressedDeleteWar();
    }
}
