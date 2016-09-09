package com.wickeddevs.easywars.ui.home.war.clanoverview;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.war.Participent;
import com.wickeddevs.easywars.data.model.war.War;

import java.util.ArrayList;

/**
 * Created by 375csptssce on 9/9/16.
 */
public interface ClanOverviewContract {

    interface View extends PView {

        void displayOverview(ArrayList<Participent> participents);

        void toggleLoading(boolean loading);
    }

    interface ViewListener extends Presenter<ClanOverviewContract.View> {

        void onCreate();

    }
}
