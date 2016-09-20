package com.wickeddevs.easywars.ui.attacks;

import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.data.service.contract.WarService;

import javax.inject.Inject;

/**
 * Created by 375csptssce on 9/20/16.
 */

public class AttacksPresenter implements AttacksContract.ViewListener{

    private AttacksContract.View view;

    private WarService warService;

    @Inject
    public AttacksPresenter(WarService warService) {
        this.warService = warService;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void selectedBase(Base base) {

    }

    @Override
    public void changeBaseAttack1() {

    }

    @Override
    public void changeBaseAttack2() {

    }

    @Override
    public void starsAttack1(int stars) {

    }

    @Override
    public void starsAttack2(int stars) {

    }

    @Override
    public void saveChanges() {

    }

    @Override
    public void registerView(AttacksContract.View activity) {
        view = activity;
    }
}
