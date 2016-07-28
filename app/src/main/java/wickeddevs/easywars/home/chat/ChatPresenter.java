package wickeddevs.easywars.home.chat;


import java.util.ArrayList;

import wickeddevs.easywars.data.Services;
import wickeddevs.easywars.data.model.Clan;
import wickeddevs.easywars.data.model.Member;
import wickeddevs.easywars.data.model.Message;
import wickeddevs.easywars.util.General;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class ChatPresenter implements ChatContract.ViewListener, Services.ChatService.MessageListener {

    final static String TAG = "ChatPresenter";

    private final ChatContract.View mChatView;
    private final Services.ChatService mChatService;
    private final Services.ClanService mClanService;

    public ChatPresenter(ChatContract.View chatView, Services.ChatService chatService, Services.ClanService clanService) {
        mChatView = chatView;
        mChatService = chatService;
        mClanService = clanService;
    }


    @Override
    public void sendMessage(String message) {
        mChatService.sendMessage(message);
        mChatView.clearSendText();
    }

    @Override
    public void start() {
        mChatService.setMessageListener(this);
    }

    @Override
    public void stop() {
        mChatService.removeMessageListener();
    }

    @Override
    public void initialMessages(final ArrayList<Message> messages) {

        mClanService.getClan(new Services.ClanService.LoadClanCallback() {
            @Override
            public void onClanLoaded(Clan clan) {
                for (Message message : messages) {
                    message.name = clan.members.get(message.uid).name;
                    message.dateTime = General.formatDateTime(message.timestamp);
                }
                mChatView.setMessages(messages);
            }
        });
    }

    @Override
    public void newMessage(final Message message) {
        mClanService.getMember(message.uid, new Services.ClanService.LoadMemberCallback() {
            @Override
            public void onMemberLoaded(Member member) {
                message.name = member.name;
                message.dateTime = General.formatDateTime(message.timestamp);
                mChatView.addMessage(message);
            }
        });
    }
}
