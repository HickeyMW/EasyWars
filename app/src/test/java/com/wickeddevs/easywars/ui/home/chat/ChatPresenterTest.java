package com.wickeddevs.easywars.ui.home.chat;

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

import com.wickeddevs.easywars.data.model.Clan;
import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.model.Message;
import com.wickeddevs.easywars.data.service.contract.ChatService;
import com.wickeddevs.easywars.data.service.contract.ClanService;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class ChatPresenterTest {

    private ArrayList<Message> messages = Lists.newArrayList(new Message("UID1", "Message Body", 203984),
            new Message("UID2", "Message Body2", 203453984), new Message("UID3", "Message Body3", 2063984));

    private String key1 = "UID1";
    private String key2 = "UID2";
    private String key3 = "UID3";
    private Member member1 = new Member("Name1", true, "UID1");
    private Member member2 = new Member("Name2", true, "UID2");
    private Member member3 = new Member("Name3", false, "UID3");
    private HashMap<String, Member> members;
    private Clan clan;

    private static List<Message> EMPTY_MESSAGES = new ArrayList<>();

    @Mock
    private ChatContract.View view;

    @Mock
    private ChatService chatService;

    @Mock
    private ClanService clanService;

//    @Captor
//    private ArgumentCaptor<ChatService.MessageListener> mMessageListenerArgumentCaptor;

    @Captor
    private ArgumentCaptor<ClanService.LoadMemberCallback> loadMemberCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<ClanService.LoadClanCallback> loadClanCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> messageBodyArgumentCaptor;

    @Captor
    private ArgumentCaptor<ChatService.MessageListener> messageListenerArgumentCaptor;

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
    public void attach_isMemberChat_setListener() {
        when(view.isAdminChat()).thenReturn(false);
        presenter.onCreate();
        verify(chatService).setMessageListener(false, messageListenerArgumentCaptor.capture());
    }

    @Test
    public void attach_isAdminChat_setListener() {
        when(view.isAdminChat()).thenReturn(true);
        presenter.onCreate();
        verify(chatService).setMessageListener(true, messageListenerArgumentCaptor.capture());
    }

    @Test
    public void loadsInitialMessages() {
        attach_isMemberChat_setListener();
        messageListenerArgumentCaptor.getValue().initialLoadComplete();
        verify(view).toggleLoading(false);
    }

    @Test
    public void addMessage() {
        attach_isMemberChat_setListener();
        messageListenerArgumentCaptor.getValue().newMessage();
        presenter.newMessage(message);
        verify(clanService).getMember(eq(message.uid), loadMemberCallbackArgumentCaptor.capture());
        loadMemberCallbackArgumentCaptor.getValue().onMemberLoaded(member1);
        verify(view).addMessage(messages.get(0));
    }

    @Test
    public void sendMessageMember() {
        attach_isMemberChat_setListener();
        String message = "Message text";
        presenter.sendMessage(message);
        verify(chatService).sendMessage(message);
    }

    @Test
    public void sendMessageAdmin() {
        attach_isAdminChat_setListener();
        String message = "Message text";
        presenter.sendMessage(message);
        verify(chatService).sendAdminMessage(message);
    }

    @Test
    public void detach_isMemberChat_removeListener() {
        attach_isMemberChat_setListener();
        presenter.onDetach();
        verify(chatService).removeMemberMessageListener();
    }

    @Test
    public void detach_isAdminChat_removeListener() {
        attach_isAdminChat_setListener();
        presenter.onDetach();
        verify(chatService).removeAdminMessageListener();
    }

}