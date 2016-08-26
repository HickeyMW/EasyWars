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
public class ChatPresenter implements ChatContract.ViewListener, ChatService.MessageListener {

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
    public void onAttach() {
        isAdminChat = view.isAdminChat();
        if (isAdminChat) {
            chatService.setMemberMessageListener(this);
        } else {
            chatService.setAdminMessageListener(this);
        }
    }

    @Override
    public void onDetach() {
        if (isAdminChat) {
            chatService.removeMemberMessageListener();
        } else {
            chatService.removeAdminMessageListener();
        }
    }


    @Override
    public void sendMessage(String message) {
        if (isAdminChat) {
            chatService.sendMemberMessage(message);
        } else {
            chatService.sendAdminMessage(message);
        }
        view.clearSendText();
    }

    @Override
    public void initialMessages(final ArrayList<Message> messages) {

        clanService.getClan(new ClanService.LoadClanCallback() {
            @Override
            public void onClanLoaded(Clan clan) {
                for (Message message : messages) {
                    message.name = clan.members.get(message.uid).name;
                    message.dateTime = General.formatDateTime(message.timestamp);
                }
                view.setMessages(messages);
            }
        });
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
}
