package com.wickeddevs.easywars.ui.startwar.info;

import com.wickeddevs.easywars.data.model.war.WarInfo;
import com.wickeddevs.easywars.data.service.contract.WarService;
import com.wickeddevs.easywars.util.Shared;

import javax.inject.Inject;

/**
 * Created by 375csptssce on 9/7/16.
 */
public class WarInfoPresenter implements WarInfoContract.ViewListener {

    private WarInfoContract.View view;
    private WarService warService;

    private int warSize = 10;
    private int hours = 23;
    private int minutes = 59;
    private String name;
    private String tag;
    private  int members;
    private boolean untilWarStart = true;

    @Inject
    public WarInfoPresenter(WarService warService) {
        this.warService = warService;
    }

    public long calculateStartTime() {
        long currentTime = System.currentTimeMillis();
        long timeElapsed = (((23 - hours) * 60) + (60 - minutes)) * 60000;
        long warStart = currentTime - timeElapsed;
        if (!untilWarStart) {
            warStart -= Shared.MILIS_IN_ONE_DAY; // Miliseconds in a day
        }
        return warStart;
    }

    @Override
    public void pressedIncreaseWarSize() {
        if (warSize != 50) {
            if (warSize == 30 || warSize == 40) {
                warSize += 10;
            } else {
                warSize += 5;
            }
            view.setWarSizeText(String.valueOf(warSize));
        }
    }

    @Override
    public void pressedDecreaseWarSize() {
        if (warSize != 10) {
            if (warSize == 40 || warSize == 50) {
                warSize -= 10;
            } else {
                warSize -= 5;
            }
            view.setWarSizeText(String.valueOf(warSize));
        }
    }

    @Override
    public void switchedUntil(boolean isWarStart) {
        untilWarStart = isWarStart;
    }

    @Override
    public void selectedEnemy(String name, String tag, int members) {
        this.name = name;
        this.tag = tag;
        this.members = members;
    }

    @Override
    public void pressedNext() {
        if (members < warSize) {
            view.displayMessage("The warsize is greater than the number of members in the opponent's clan");
        } else {
            warService.saveWarInfo(new WarInfo(name, tag, calculateStartTime()));
            view.navigateToWarOrderUi(tag, warSize);
        }
    }

    @Override
    public void registerView(WarInfoContract.View activity) {
        view = activity;
    }
}
