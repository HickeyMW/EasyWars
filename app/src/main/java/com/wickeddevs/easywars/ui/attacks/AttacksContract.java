package com.wickeddevs.easywars.ui.attacks;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.war.Attack;
import com.wickeddevs.easywars.data.model.war.Base;

import java.util.ArrayList;

/**
 * Created by 375csptssce on 9/20/16.
 */

public interface AttacksContract {

    interface View extends PView {

        String getParticipentKey();

        void displayAttack1(Attack attack);

        void displayAttack2(Attack attack);

        void showSecondAttack(boolean shouldShow);

        void displayBaseSelector(ArrayList<Base> bases);

        void toggleLoading(boolean loading);

        void dismiss();
    }

    interface ViewListener extends Presenter<View> {

        void onCreate();

        void selectedBase(Base base);

        void changeBaseAttack1();

        void changeBaseAttack2();

        void starsAttack1(int stars);

        void starsAttack2(int stars);

        void saveChanges();
    }
}
