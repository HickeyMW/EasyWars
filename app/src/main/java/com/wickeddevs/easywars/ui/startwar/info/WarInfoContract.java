package com.wickeddevs.easywars.ui.startwar.info;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;

/**
 * Created by 375csptssce on 9/7/16.
 */
public interface WarInfoContract {

    interface View extends PView {

        void setWarSizeText(String warSize);

        void navigateToWarOrderUi(String clanTag, int warSize);
    }

    interface ViewListener extends Presenter<WarInfoContract.View> {

        void selectedEnemy(String name, String tag, int members);

        void pressedIncreaseWarSize();

        void pressedDecreaseWarSize();

        void switchedUntil(boolean isWarStart);

        void pressedNext(int hours, int minutes);
    }
}
