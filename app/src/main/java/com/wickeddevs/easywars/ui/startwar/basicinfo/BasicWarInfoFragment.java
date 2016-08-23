package com.wickeddevs.easywars.ui.startwar.basicinfo;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.base.BasePresenterFragment;
import com.wickeddevs.easywars.dagger.Injector;
import com.wickeddevs.easywars.databinding.FragmentBasicWarInfoBinding;
import com.wickeddevs.easywars.ui.shared.search.SearchClansActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasicWarInfoFragment extends BasePresenterFragment<BasicWarInfoContract.ViewListener>
        implements BasicWarInfoContract.View, CompoundButton.OnCheckedChangeListener {

    @Inject
    public BasicWarInfoContract.ViewListener presenter;

    private FragmentBasicWarInfoBinding binding;
    private boolean perfomingToggle = false;
    private String clanName;
    private String clanTag;
    private BasicWarInfoFragmentListener listener;

    public BasicWarInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_basic_war_info, container, false);
        binding.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.decreaseWarSize();
            }
        });
        binding.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.increaseWarSize();
            }
        });
        binding.tvHours.addTextChangedListener(new TextWatcher() {
            int newNumber = -1;
            int oldNumber = -1;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    oldNumber = Integer.valueOf(charSequence.toString());
                } catch (NumberFormatException e) {
                    oldNumber = -1;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    newNumber = Integer.valueOf(charSequence.toString());
                } catch (NumberFormatException e) {
                    newNumber = -1;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (newNumber > 23)
                {
                    String number = String.valueOf(oldNumber);
                    editable.replace(0, editable.length(), number, 0, number.length());
                }
            }
        });
        binding.tvMinutes.addTextChangedListener(new TextWatcher() {
            int newNumber = -1;
            int oldNumber = -1;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    oldNumber = Integer.valueOf(charSequence.toString());
                } catch (NumberFormatException e) {
                    oldNumber = -1;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    newNumber = Integer.valueOf(charSequence.toString());
                } catch (NumberFormatException e) {
                    newNumber = -1;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (newNumber > 59)
                {
                    String number = String.valueOf(oldNumber);
                    editable.replace(0, editable.length(), number, 0, number.length());
                }
            }
        });
        binding.layoutOpponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = SearchClansActivity.createIntent(getContext(), SearchClansActivity.STARTED_FOR_WAR);
                startActivityForResult(i, 0);
            }
        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int warsize = Integer.valueOf(binding.tvWarSize.getText().toString());
                long startTime = presenter.getStartTimeMilis(System.currentTimeMillis(), binding.rbWarEnd.isChecked());
                listener.startWarInfo(warsize, startTime, clanName, clanTag);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            clanTag = data.getStringExtra("clanTag");
            clanName = data.getStringExtra("clanName");
            binding.tvOpponentName.setText(clanName);
            Glide.with(getContext()).load(data.getStringExtra("imageUrl")).centerCrop().into(binding.ivBadge);
            binding.btnNext.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BasicWarInfoFragmentListener) {
            listener = (BasicWarInfoFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BasicWarInfoFragmentListener");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!perfomingToggle) {
            if (compoundButton == binding.rbWarStart) {
                perfomingToggle = true;
                binding.rbWarStart.setChecked(b);
                binding.rbWarEnd.setChecked(!b);
                perfomingToggle = false;
            } else {
                perfomingToggle = true;
                binding.rbWarEnd.setChecked(b);
                binding.rbWarStart.setChecked(!b);
                perfomingToggle = false;
            }
        }
    }

    @Override
    protected BasicWarInfoContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
        }
        return presenter;
    }

    @Override
    public void toggleProgressBar(boolean loading) {

    }

    @Override
    public void displayMessage(String message) {

    }

    @Override
    public void setWarSizeText(String warSize) {
        binding.tvWarSize.setText(warSize);
    }

    public interface BasicWarInfoFragmentListener {
        void startWarInfo(int warSize, long startTime, String name, String clanTag);
    }
}
