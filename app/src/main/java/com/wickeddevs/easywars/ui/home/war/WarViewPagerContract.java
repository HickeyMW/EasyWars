package com.wickeddevs.easywars.ui.home.war;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;

/**
 * Created by 375csptssce on 9/6/16.
 */
public interface WarViewPagerContract {

    interface View extends PView {

        void displayUi(boolean activeWar);

        void toggleLoading(boolean loading);

        void setTitle(String title);

        void setSubTitle(String subTitle);

        boolean isAdmin();
    }

    interface ViewListener extends Presenter<View> {

        void onResume();
    }
}
