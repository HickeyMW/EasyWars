package com.wickeddevs.easywars.ui.startwar.basicinfo;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;

/**
 * Created by 375csptssce on 8/18/16.
 */
public interface BasicWarInfoContract {

    interface View extends PView {

        void setWarSizeText(String warSize);
    }

    interface ViewListener extends Presenter<BasicWarInfoContract.View> {

        void increaseWarSize();

        void decreaseWarSize();

        long getStartTimeMilis(long currentTimeMilis, boolean tilWarEnd);
    }
}
