package com.wickeddevs.easywars.ui.home.chat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wickeddevs.easywars.data.model.Message;
import com.wickeddevs.easywars.data.service.contract.ChatService;
import com.wickeddevs.easywars.data.service.contract.ClanService;
import com.wickeddevs.easywars.util.Testing;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class ChatPresenterTest {

    private Message message = Testing.randomMessage();
    private String messageBody = Testing.randomString();

    @Mock
    private ChatContract.View view;

    @Mock
    private ChatService chatService;

    @Mock
    private ClanService clanService;

    @Captor
    private ArgumentCaptor<ClanService.LoadMemberCallback> loadMemberCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<ChatService.MessageListener> messageListenerArgumentCaptor;

    private ChatPresenter presenter;

    @Before
    public void setupLoadingSplashPresenter() {
        MockitoAnnotations.initMocks(this);
        presenter = new ChatPresenter(chatService, clanService);
        presenter.registerView(view);
    }

    @Test
    public void attach_isMemberChat_setListener() {
        when(view.isAdminChat()).thenReturn(false);
        presenter.onCreate();
        verify(chatService).setMessageListener(eq(false), messageListenerArgumentCaptor.capture());
    }

    @Test
    public void attach_isAdminChat_setListener() {
        when(view.isAdminChat()).thenReturn(true);
        presenter.onCreate();
        verify(chatService).setMessageListener(eq(true), messageListenerArgumentCaptor.capture());
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
        messageListenerArgumentCaptor.getValue().newMessage(message);
        verify(clanService).getMember(eq(message.uid), loadMemberCallbackArgumentCaptor.capture());
        loadMemberCallbackArgumentCaptor.getValue().onMemberLoaded(Testing.randomMember(message.uid));
        verify(view).addMessage(message);
    }

    @Test
    public void sendMessageMember() {
        attach_isMemberChat_setListener();
        presenter.sendMessage(messageBody);
        verify(chatService).sendMessage(false, messageBody);
    }

    @Test
    public void sendMessageAdmin() {
        attach_isAdminChat_setListener();
        presenter.sendMessage(messageBody);
        verify(chatService).sendMessage(true, messageBody);
    }

    @Test
    public void detach_isMemberChat_removeListener() {
        attach_isMemberChat_setListener();
        presenter.onDestroy();
        verify(chatService).removeMessageListener();
    }

    @Test
    public void detach_isAdminChat_removeListener() {
        attach_isAdminChat_setListener();
        presenter.onDestroy();
        verify(chatService).removeMessageListener();
    }

}