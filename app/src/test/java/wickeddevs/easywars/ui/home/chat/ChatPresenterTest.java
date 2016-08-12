package wickeddevs.easywars.ui.home.chat;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wickeddevs.easywars.data.model.Clan;
import wickeddevs.easywars.data.model.Member;
import wickeddevs.easywars.data.model.Message;
import wickeddevs.easywars.data.service.contract.ChatService;
import wickeddevs.easywars.data.service.contract.ClanService;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class ChatPresenterTest {

    private ArrayList<Message> messages = Lists.newArrayList(new Message("KEY1", "Message Body", 203984),
            new Message("KEY3", "Message Body2", 203453984), new Message("KEY3", "Message Body3", 2063984));

    private String key1 = "KEY1";
    private String key2 = "KEY2";
    private String key3 = "KEY3";
    private Member member1 = new Member(true, "Name1", "UID1");
    private Member member2 = new Member(true, "Name2", "UID2");
    private Member member3 = new Member(false, "Name3", "UID3");
    private HashMap<String, Member> members;
    private Clan clan;

    private static List<Message> EMPTY_MESSAGES = new ArrayList<>();

    @Mock
    private ChatContract.View view;

    @Mock
    private ChatService chatService;

    @Mock
    private ClanService clanService;

    @Captor
    private ArgumentCaptor<ChatService.MessageListener> mMessageListenerArgumentCaptor;

    @Captor
    private ArgumentCaptor<ClanService.LoadMemberCallback> mLoadMemberCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<ClanService.LoadClanCallback> mLoadClanCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> mMessageBodyArgumentCaptor;

    private ChatPresenter presenter;

    @Before
    public void setupLoadingSplashPresenter() {
        MockitoAnnotations.initMocks(this);
        presenter = new ChatPresenter(chatService, clanService);
        presenter.registerView(view);
        members = new HashMap<>();
        members.put(key1, member1);
        members.put(key2, member2);
        members.put(key3, member3);
        clan = new Clan();
        clan.members = members;
    }

    @Test
    public void startListeningForMessages_loadsInitialMessages() {
        presenter.onAttach();
        verify(chatService).setMessageListener(mMessageListenerArgumentCaptor.capture());
        mMessageListenerArgumentCaptor.getValue().initialMessages(messages);
        verify(clanService).getClan(mLoadClanCallbackArgumentCaptor.capture());
        mLoadClanCallbackArgumentCaptor.getValue().onClanLoaded(clan);
        verify(view).setMessages(messages);
    }

    @Test
    public void newMessageFromService_messageSentToView() {
        presenter.onAttach();
        verify(chatService).setMessageListener(mMessageListenerArgumentCaptor.capture());
        Message message = messages.get(1);
        mMessageListenerArgumentCaptor.getValue().newMessage(message);
        verify(clanService).getMember(eq(message.uid), mLoadMemberCallbackArgumentCaptor.capture());
        mLoadMemberCallbackArgumentCaptor.getValue().onMemberLoaded(member3);
        verify(view).addMessage(message);
    }

    @Test
    public void sendMessage_isSent() {
        String body = "This is the text of a test message";
        presenter.sendMessage(body);
        verify(chatService).sendMessage(mMessageBodyArgumentCaptor.capture());
        verify(view).clearSendText();
        assertEquals(mMessageBodyArgumentCaptor.getValue(), body);
    }

    @Test
    public void stopListeningForMessages() {
        presenter.onDetach();
        verify(chatService).removeMessageListener();
    }

}