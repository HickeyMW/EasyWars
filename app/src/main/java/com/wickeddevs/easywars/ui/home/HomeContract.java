package com.wickeddevs.easywars.ui.home;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.model.api.ApiClan;

/**
 * Created by 375csptssce on 8/16/16.
 */
public interface HomeContract {

    interface View extends PView {

        void navigateToNoClanUi();

        void navigateToLoadingSplash();

        void displayUi(Member member, ApiClan apiClan);
    }

    interface ViewListener extends Presenter<HomeContract.View> {

        void pressedLogout();
    }
}
