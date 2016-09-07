package com.wickeddevs.easywars.ui.startwar.warorder;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.api.ApiClan;

import java.util.ArrayList;

/**
 * Created by 375csptssce on 8/22/16.
 */
public interface WarOrderContract {

    interface View extends PView {

        void removeMember(int position);

        void undoRemoveMember();

        void displayApiClan(ApiClan apiClan);

        void displayThSelector();

        void displayMember(String name, int thLevel);

        String getClanTag();

        int getWarSize();

        void setRemainingText(String remainingText);

        void toggleLoading(boolean loading);

        void allowDone(boolean allow);

        void dismiss();
    }

    interface ViewListener extends Presenter<WarOrderContract.View> {

        void onCreate();

        void selectedName(String name, int position);

        void selectedTownHall(int thLevel);

        void pressedUndo();

        void pressedDone();
    }
}
