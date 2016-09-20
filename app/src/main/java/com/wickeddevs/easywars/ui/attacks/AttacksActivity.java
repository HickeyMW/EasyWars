package com.wickeddevs.easywars.ui.attacks;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.base.BasePresenterActivity;
import com.wickeddevs.easywars.dagger.component.DaggerServiceComponent;
import com.wickeddevs.easywars.dagger.component.DaggerViewInjectorComponent;
import com.wickeddevs.easywars.data.model.war.Attack;
import com.wickeddevs.easywars.data.model.war.Base;
import com.wickeddevs.easywars.util.Shared;

import java.util.ArrayList;

import javax.inject.Inject;

public class AttacksActivity extends BasePresenterActivity<AttacksContract.ViewListener> implements
        AttacksContract.View {

    TextView tvBaseName1;
    TextView tvStatus1;
    TextView tvStars1;
    ImageView ivBase1;
    RadioGroup rgrpStatus1;
    RadioGroup rgrpStars1;
    RadioButton rbClaimed1;
    RadioButton rbAttacked1;
    RadioButton rb0Star1;
    RadioButton rb1Star1;
    RadioButton rb2Star1;
    RadioButton rb3Star1;
    TextView tvAttack2;
    CardView cardView2;
    TextView tvBaseName2;
    TextView tvStatus2;
    TextView tvStars2;
    ImageView ivBase2;
    RadioGroup rgrpStatus2;
    RadioGroup rgrpStars2;
    RadioButton rbClaimed2;
    RadioButton rbAttacked2;
    RadioButton rb0Star2;
    RadioButton rb1Star2;
    RadioButton rb2Star2;
    RadioButton rb3Star2;


    @Inject
    public AttacksContract.ViewListener presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attacks);
        tvBaseName1 = (TextView) findViewById(R.id.tvBaseName1);
        tvStatus1 = (TextView) findViewById(R.id.tvStatus1);
        tvStars1 = (TextView) findViewById(R.id.tvStars1);
        ivBase1 = (ImageView) findViewById(R.id.ivBase1);
        rgrpStatus1 = (RadioGroup) findViewById(R.id.rgrpStatus1);
        rgrpStars1 = (RadioGroup) findViewById(R.id.rgrpStars1);
        rbClaimed1 = (RadioButton) findViewById(R.id.rbClaimed1);
        rbAttacked1 = (RadioButton) findViewById(R.id.rbAttacked1);
        rb0Star1 = (RadioButton) findViewById(R.id.rb0Star1);
        rb1Star1 = (RadioButton) findViewById(R.id.rb1Star1);
        rb2Star1 = (RadioButton) findViewById(R.id.rb2Star1);
        rb3Star1 = (RadioButton) findViewById(R.id.rb3Star1);
        tvAttack2 = (TextView) findViewById(R.id.tvAttack2);
        cardView2 = (CardView) findViewById(R.id.cardView2);
        tvBaseName2 = (TextView) findViewById(R.id.tvBaseName2);
        tvStatus2 = (TextView) findViewById(R.id.tvStatus2);
        tvStars2 = (TextView) findViewById(R.id.tvStars2);
        ivBase2 = (ImageView) findViewById(R.id.ivBase2);
        rgrpStatus2 = (RadioGroup) findViewById(R.id.rgrpStatus2);
        rgrpStars2 = (RadioGroup) findViewById(R.id.rgrpStars2);
        rbClaimed2 = (RadioButton) findViewById(R.id.rbClaimed2);
        rbAttacked2 = (RadioButton) findViewById(R.id.rbAttacked2);
        rb0Star2 = (RadioButton) findViewById(R.id.rb0Star2);
        rb1Star2 = (RadioButton) findViewById(R.id.rb1Star2);
        rb2Star2 = (RadioButton) findViewById(R.id.rb2Star2);
        rb3Star2 = (RadioButton) findViewById(R.id.rb3Star2);
        presenter.onCreate();
    }

    @Override
    protected AttacksContract.ViewListener getPresenter() {
        if(presenter == null){
            DaggerViewInjectorComponent.builder()
                    .serviceComponent(DaggerServiceComponent.create())
                    .build().inject(this);
        }
        return presenter;
    }

    @Override
    public void displayAttack1(Attack attack) {
        tvBaseName1.setText(attack.baseName);
        ivBase1.setImageResource(Shared.getThResource(attack.thLevel));
        if (attack == null) {
            showStatus1(false);
            showStars1(false);
        } else {
            showStatus1(true);
            if (attack.stars == -1) {
                showStars1(false);
                rbClaimed1.setChecked(true);
            } else {
                showStars1(true);
                switch (attack.stars) {
                    case 0:
                        rb0Star1.setChecked(true);
                        break;
                    case 1:
                        rb1Star1.setChecked(true);
                        break;
                    case 2:
                        rb2Star1.setChecked(true);
                        break;
                    case 3:
                        rb3Star1.setChecked(true);
                        break;
                }
            }
        }
    }

    @Override
    public void displayAttack2(Attack attack) {
        tvBaseName2.setText(attack.baseName);
        ivBase2.setImageResource(Shared.getThResource(attack.thLevel));
        if (attack == null) {
            showStatus2(false);
            showStars2(false);
        } else {
            showStatus2(true);
            if (attack.stars == -1) {
                showStars2(false);
                rbClaimed2.setChecked(true);
            } else {
                showStars2(true);
                switch (attack.stars) {
                    case 0:
                        rb0Star2.setChecked(true);
                        break;
                    case 1:
                        rb1Star2.setChecked(true);
                        break;
                    case 2:
                        rb2Star2.setChecked(true);
                        break;
                    case 3:
                        rb3Star2.setChecked(true);
                        break;
                }
            }
        }
    }

    @Override
    public void showSecondAttack(boolean shouldShow) {
        if (shouldShow) {
            showAttack2(true);
        } else {
            showAttack2(false);
            showStatus2(false);
            showStars2(false);
        }
    }

    @Override
    public void displayBaseSelector(ArrayList<Base> bases) {

    }

    @Override
    public void dismiss() {
        finish();
    }

    private void showStatus1(boolean shouldShow) {
        if (shouldShow) {
            tvStatus1.setVisibility(View.VISIBLE);
            rgrpStatus1.setVisibility(View.VISIBLE);
        } else {
            tvStatus1.setVisibility(View.INVISIBLE);
            rgrpStatus1.setVisibility(View.INVISIBLE);
        }
    }

    private void showStars1(boolean shouldShow) {
        if (shouldShow) {
            tvStars1.setVisibility(View.VISIBLE);
            rgrpStars1.setVisibility(View.VISIBLE);
        } else {
            tvStars1.setVisibility(View.INVISIBLE);
            rgrpStars1.setVisibility(View.INVISIBLE);
        }
    }

    private void showAttack2(boolean shouldShow) {
        if (shouldShow) {
            tvAttack2.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);
        } else {
            tvAttack2.setVisibility(View.INVISIBLE);
            cardView2.setVisibility(View.INVISIBLE);
        }
    }

    private void showStatus2(boolean shouldShow) {
        if (shouldShow) {
            tvStatus2.setVisibility(View.VISIBLE);
            rgrpStatus2.setVisibility(View.VISIBLE);
        } else {
            tvStatus2.setVisibility(View.INVISIBLE);
            rgrpStatus2.setVisibility(View.INVISIBLE);
        }
    }

    private void showStars2(boolean shouldShow) {
        if (shouldShow) {
            tvStars2.setVisibility(View.VISIBLE);
            rgrpStars2.setVisibility(View.VISIBLE);
        } else {
            tvStars2.setVisibility(View.INVISIBLE);
            rgrpStars2.setVisibility(View.INVISIBLE);
        }
    }
}
