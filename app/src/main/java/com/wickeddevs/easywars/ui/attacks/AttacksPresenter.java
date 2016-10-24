package com.wickeddevs.easywars.ui.attacks;

import com.wickeddevs.easywars.data.model.war.Attack;
import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.data.model.war.Participent;
import com.wickeddevs.easywars.data.model.war.War;
import com.wickeddevs.easywars.data.service.contract.WarService;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by 375csptssce on 9/20/16.
 */

public class AttacksPresenter implements AttacksContract.ViewListener{

    private AttacksContract.View view;
    private WarService warService;

    private ArrayList<Base> bases;
    private Attack attack1 = null;
    private Attack attack2 = null;
    private boolean isChangingBaseOne;
    private boolean isDisplayingSecondAttack = false;

    @Inject
    public AttacksPresenter(WarService warService) {
        this.warService = warService;
    }

    @Override
    public void onCreate() {
        final WarService.LoadAttacksCallback callback = new WarService.LoadAttacksCallback() {
            @Override
            public void onLoaded(ArrayList<Attack> attacks) {
                view.toggleLoading(false);
                switch (attacks.size()) {
                    case 0:
                        view.displayAttack1(null);
                        view.showSecondAttack(false);
                        break;
                    case 1:
                        attack1 = attacks.get(0);
                        view.displayAttack1(attack1);
                        view.displayAttack2(null);
                        break;
                    case 2:
                        attack1 = attacks.get(0);
                        attack2 = attacks.get(1);
                        view.displayAttack1(attack1);
                        view.displayAttack2(attack2);
                        isDisplayingSecondAttack = true;
                        break;
                }
            }
        };

        view.toggleLoading(true);
        warService.getLatestWar(new WarService.LoadWarCallback() {
            @Override
            public void onLoaded(War war) {
                bases = war.bases;
                String key = view.getParticipentKey();
                if (key == null) {
                    warService.getLatestWarAttacks(callback);
                } else {
                    warService.getLatestWarAttacks(key, callback);
                }
            }
        });

    }

    @Override
    public void selectedBase(Base base) {
        if (isChangingBaseOne) {
            if (base == null) {
                if (attack2 == null) {
                    view.showSecondAttack(false);
                }
                warService.deleteAttack(attack1);
                attack1 = null;
                view.displayAttack1(null);

            } else {
                if (attack1 == null) {
                    attack1 = new Attack();
                }
                attack1.baseName = base.name;
                attack1.base = Integer.valueOf(base.key);
                attack1.stars = -1;
                attack1.thLevel = base.thLevel;
                if (!isDisplayingSecondAttack) {
                    view.showSecondAttack(true);
                    view.displayAttack2(null);
                    isDisplayingSecondAttack = true;
                }
                view.displayAttack1(attack1);
            }
        } else {
            if (base == null) {
                warService.deleteAttack(attack2);
                attack2 = null;
                view.displayAttack2(null);
            } else {
                if (attack2 == null) {
                    attack2 = new Attack();
                    attack2.baseName = base.name;
                    attack2.stars = -1;
                    attack2.thLevel = base.thLevel;
                }
                attack2.base = Integer.valueOf(base.key);
                view.displayAttack2(attack2);
            }
        }
    }

    @Override
    public void changeBaseAttack1() {
        isChangingBaseOne = true;
        view.displayBaseSelector(bases);
    }

    @Override
    public void changeBaseAttack2() {
        isChangingBaseOne = false;
        view.displayBaseSelector(bases);
    }

    @Override
    public void starsAttack1(int stars) {
        if (attack1 != null) {
            attack1.stars = stars;
        }
    }

    @Override
    public void starsAttack2(int stars) {
        if (attack2 != null) {
            attack2.stars = stars;
        }
    }

    @Override
    public void saveChanges() {
        final String key = view.getParticipentKey();
        if (key != null) {
            if (attack1 != null) {
                attack1.uid = key;
            }
            if (attack2 != null) {
                attack2.uid = key;
            }
        }

        if (attack1 != null) {
            warService.saveAttack(attack1);
        }

        if (attack2 != null) {
            warService.saveAttack(attack2);
        }
        view.dismiss();
    }

    @Override
    public void registerView(AttacksContract.View activity) {
        view = activity;
    }
}
