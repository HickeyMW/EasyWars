package com.wickeddevs.easywars.ui.warsettings;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.war.Participent;

import java.util.ArrayList;

/**
 * Created by 375csptssce on 9/22/16.
 */

public interface WarSettingsContract {

    interface View extends PView {

        void displayParticipents(ArrayList<Participent> participents);

        void toggleLoading(boolean loading);
    }

    interface ViewListener extends Presenter<WarSettingsContract.View> {

        void onCreate();

        void deleteWar();
    }
}
