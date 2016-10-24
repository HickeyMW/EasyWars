package com.wickeddevs.easywars.ui.home;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.databinding.FragmentHomeBottomBarBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeBottomBarFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBottomBarBinding binding;

    private int selectedTab = 0;
    private TabSelectedListener listener;

    public HomeBottomBarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_bottom_bar, container, false);

        binding.layoutWar.setOnClickListener(this);
        binding.layoutChat.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (TabSelectedListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement TabSelectedListener");
        }
    }

    @Override
    public void onClick(View v) {
        int previousTab = -1;

        int selectedTextColor = ContextCompat.getColor(getContext(), R.color.tab_text);
        int unselectedTextColor = ContextCompat.getColor(getContext(), R.color.tab_text_dark);

        switch (v.getId()) {
            case R.id.layoutWar:
                if (selectedTab != 0) {
                    binding.tvWar.setTextColor(selectedTextColor);
                    binding.ivWar.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_war_planner));
                    previousTab = selectedTab;
                    selectedTab = 0;
                }
                break;
            case R.id.layoutChat:
                if (selectedTab != 1) {
                    binding.tvChat.setTextColor(selectedTextColor);
                    binding.ivChat.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_chat));
                    previousTab = selectedTab;
                    selectedTab = 1;
                }
                break;
        }

        switch (previousTab) {
            case 0:
                binding.tvWar.setTextColor(unselectedTextColor);
                binding.ivWar.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_war_planner_dark));
                break;
            case 1:
                binding.tvChat.setTextColor(unselectedTextColor);
                binding.ivChat.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_chat_dark));
                break;
        }

        listener.selectedTab(selectedTab);
    }

    public interface TabSelectedListener {
        void selectedTab(int tab);
    }
}
