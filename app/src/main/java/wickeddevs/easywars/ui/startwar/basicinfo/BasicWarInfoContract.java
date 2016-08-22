package wickeddevs.easywars.ui.startwar.basicinfo;

import java.util.ArrayList;

import wickeddevs.easywars.base.PView;
import wickeddevs.easywars.base.Presenter;
import wickeddevs.easywars.data.model.api.ApiClan;

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
