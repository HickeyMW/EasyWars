package com.wickeddevs.easywars.ui.home.chat;


import java.util.ArrayList;

import com.wickeddevs.easywars.data.model.Clan;
import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.model.Message;
import com.wickeddevs.easywars.data.service.contract.ChatService;
import com.wickeddevs.easywars.data.service.contract.ClanService;
import com.wickeddevs.easywars.util.General;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class ChatPresenter implements ChatContract.ViewListener {

    final static String TAG = "ChatPresenter";

    private ChatContract.View view;
    private ChatService chatService;
    private ClanService clanService;
    private boolean isAdminChat;

    public ChatPresenter(ChatService chatService, ClanService clanService) {
        this.chatService = chatService;
        this.clanService = clanService;
    }

    @Override
    public void registerView(ChatContract.View activity) {
        view = activity;
    }


    @Override
    public void onCreate() {
        isAdminChat = view.isAdminChat();
        view.toggleLoading(true);
        chatService.setMessageListener(isAdminChat, new ChatService.MessageListener() {
            @Override
            public void initialLoadComplete() {
                view.toggleLoading(false);
            }

            @Override
            public void newMessage(final Message message) {
                clanService.getMember(message.uid, new ClanService.LoadMemberCallback() {
                    @Override
                    public void onMemberLoaded(Member member) {
                        message.name = member.name;
                        message.dateTime = General.formatDateTime(message.timestamp);
                        view.addMessage(message);
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        chatService.removeMessageListener();
    }

    @Override
    public void sendMessage(String message) {
        chatService.sendMessage(view.isAdminChat(), message);
        view.clearSendText();
    }
}
