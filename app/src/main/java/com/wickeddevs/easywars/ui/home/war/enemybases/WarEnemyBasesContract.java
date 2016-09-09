package com.wickeddevs.easywars.ui.home.war.enemybases;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.war.War;

/**
 * Created by 375csptssce on 8/16/16.
 */
public interface WarEnemyBasesContract {

    interface View extends PView {

        void displayEnemyBases(War war);

        void toggleLoading(boolean loading);
    }

    interface ViewListener extends Presenter<WarEnemyBasesContract.View> {

        void onCreate();
    }
}
