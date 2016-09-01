package com.wickeddevs.easywars.ui.startwar.warorder;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by 375csptssce on 8/22/16.
 */
public interface WarOrderContract {

    interface View extends PView {

        void displayApiClan(ApiClan apiClan);

        String getClanTag();
    }

    interface ViewListener extends Presenter<WarOrderContract.View> {

        void onCreate();


    }
}
