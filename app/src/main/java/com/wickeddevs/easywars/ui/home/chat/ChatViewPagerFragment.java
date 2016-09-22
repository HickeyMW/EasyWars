package com.wickeddevs.easywars.ui.home.chat;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wickeddevs.easywars.R;
import com.wickeddevs.easywars.adapters.viewpager.ChatViewPagerAdapter;
import com.wickeddevs.easywars.databinding.FragmentChatViewPagerBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatViewPagerFragment extends Fragment {

    private static final String IS_ADMIN = "IS_ADMIN";

    FragmentChatViewPagerBinding binding;

    public ChatViewPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        boolean isAdmin = getArguments().getBoolean(IS_ADMIN);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_view_pager, container, false);
        ChatViewPagerAdapter chatViewPagerAdapter = new ChatViewPagerAdapter(getChildFragmentManager(), isAdmin);
        binding.viewPager.setAdapter(chatViewPagerAdapter);
        if (isAdmin) {
            binding.tabLayout.setupWithViewPager(binding.viewPager);
        } else {
            binding.tabLayout.setVisibility(View.GONE);
        }
        //navigationDrawerProvider.setupDrawer(binding.toolbar);
        //binding.toolbar.setTitle("Chat");
        return binding.getRoot();
    }

    public static ChatViewPagerFragment getInstance(boolean isAdmin) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_ADMIN, isAdmin);
        ChatViewPagerFragment chatViewPagerFragment = new ChatViewPagerFragment();
        chatViewPagerFragment.setArguments(bundle);
        return chatViewPagerFragment;
    }
}
