package wickeddevs.easywars.home.chat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;

import wickeddevs.easywars.data.Services;
import wickeddevs.easywars.data.model.Member;
import wickeddevs.easywars.data.model.Message;
import wickeddevs.easywars.loadingsplash.LoadingSplashPresenter;
import wickeddevs.easywars.util.General;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class ChatPresenterTest {

    @Mock
    private Services.ChatService mChatService;

    @Mock
    private Services.ClanService mClanService;

    @Mock
    private ChatContract.View mChatView;

    @Captor
    private ArgumentCaptor<Services.ChatService.MessageListener> mMessageListenerArgumentCaptor;

    @Captor
    private ArgumentCaptor<Services.ClanService.LoadMemberCallback> mLoadMemberCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> mMessageBodyArgumentCaptor;

    private ChatPresenter mChatPresenter;

    @Before
    public void setupLoadingSplashPresenter() {
        MockitoAnnotations.initMocks(this);

        mChatPresenter = new ChatPresenter(mChatView, mChatService, mClanService);
    }

    @Test
    public void startListeningForMessages_isListening_presentsMessages() {
        mChatPresenter.start();
        verify(mChatService).setMessageListener(mMessageListenerArgumentCaptor.capture());
        String name = "Test Name";
        String body = "Test message body";
        String uid = "FOI3209JF302";
        long dateTime = Calendar.getInstance().getTimeInMillis() - 3600000;
        mMessageListenerArgumentCaptor.getValue().newMessage(new Message(uid, body, dateTime));
        verify(mClanService).getMember(eq(uid), mLoadMemberCallbackArgumentCaptor.capture());
        mLoadMemberCallbackArgumentCaptor.getValue().onMemberLoaded(new Member(false, name, uid), false);
        verify(mChatView).addMessage(name, body, General.formatDateTime(dateTime), false);
    }

    @Test
    public void sendMessage_isSent() {
        String body = "This is the text of a test message";
        mChatPresenter.sendMessage(body);
        verify(mChatService).sendMessage(mMessageBodyArgumentCaptor.capture());
        assertEquals(mMessageBodyArgumentCaptor.getValue(), body);
    }

    @Test
    public void stopListeningForMessages() {
        mChatPresenter.stop();
        verify(mChatService).removeMessageListener();
    }

}