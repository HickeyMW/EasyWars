package wickeddevs.easywars.ui.home.chat;


import java.util.ArrayList;

import wickeddevs.easywars.data.model.Clan;
import wickeddevs.easywars.data.model.Member;
import wickeddevs.easywars.data.model.Message;
import wickeddevs.easywars.data.service.contract.ChatService;
import wickeddevs.easywars.data.service.contract.ClanService;
import wickeddevs.easywars.util.General;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class ChatPresenter implements ChatContract.ViewListener, ChatService.MessageListener {

    final static String TAG = "ChatPresenter";

    private ChatContract.View view;
    private ChatService chatService;
    private ClanService clanService;

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
        chatService.setMessageListener(this);
    }

    @Override
    public void onDetach() {
        chatService.removeMessageListener();
    }


    @Override
    public void sendMessage(String message) {
        chatService.sendMessage(message);
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
