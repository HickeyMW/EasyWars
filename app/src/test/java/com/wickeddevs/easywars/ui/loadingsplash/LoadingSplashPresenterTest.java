package com.wickeddevs.easywars.ui.loadingsplash;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wickeddevs.easywars.data.model.User;
import com.wickeddevs.easywars.data.service.contract.UserService;

/**
 * Created by 375csptssce on 7/26/16.
 */
public class LoadingSplashPresenterTest {

    private LoadingSplashPresenter presenter;

    @Mock
    private LoadingSplashContract.View view;

    @Mock
    private UserService userService;

    @Captor
    private ArgumentCaptor<UserService.LoadUserCallback> loadUserCallbackArgumentCaptor;

    @Before
    public void setupLoadingSplashPresenter() {
        MockitoAnnotations.initMocks(this);
        presenter = new LoadingSplashPresenter(userService);
        presenter.registerView(view);
    }

    @Test
    public void attach_isNotLoggedIn_NavigateToLogInUi() {
        when(userService.isLoggedIn()).thenReturn(false);
        presenter.onAttach();
        verify(userService).isLoggedIn();
        verify(view).navigateToLoginUi();
    }

    @Test
    public void retunedFromLogin_loginSuccessful() {
        presenter.returnedFromLogin(true);
    }

    @Test
    public void retunedFromLogin_loginFailed() {
        presenter.returnedFromLogin(false);
        verify(view).displayMessage(anyString());
    }

    @Test
    public void attach_isLoggedIn_stateBlank_navigateNoClanUi() {
        User user = new User(User.STATE_BLANK, "");
        when(userService.isLoggedIn()).thenReturn(true);
        presenter.onAttach();
        verify(userService).isLoggedIn();
        verify(userService).getUser(loadUserCallbackArgumentCaptor.capture());
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(view).navigateToNoClanUi();
    }

    @Test
    public void attach_isLoggedIn_stateCreating_navigateCreatingClanUi() {
        User user = new User(User.STATE_CREATING, "");
        when(userService.isLoggedIn()).thenReturn(true);
        presenter.onAttach();
        verify(userService).isLoggedIn();
        verify(userService).getUser(loadUserCallbackArgumentCaptor.capture());
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(view).navigateToCreatingClanUi();
    }

    @Test
    public void attach_isLoggedIn_stateJoining_navigateJoiningClanUi() {
        User user = new User(User.STATE_JOINING, "");
        when(userService.isLoggedIn()).thenReturn(true);
        presenter.onAttach();
        verify(userService).isLoggedIn();
        verify(userService).getUser(loadUserCallbackArgumentCaptor.capture());
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(view).navigateToJoiningClanUi();
    }

    @Test
    public void attach_isLoggedIn_stateMember_navigateToHomeUi() {
        User user = new User(User.STATE_MEMBER, "");
        when(userService.isLoggedIn()).thenReturn(true);
        presenter.onAttach();
        verify(userService).isLoggedIn();
        verify(userService).getUser(loadUserCallbackArgumentCaptor.capture());
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(view).navigateToHomeUi();
    }

    @Test
    public void attach_isLoggedIn_stateAdmin_navigateToHomeUi() {
        User user = new User(User.STATE_ADMIN, "");
        when(userService.isLoggedIn()).thenReturn(true);
        presenter.onAttach();
        verify(userService).isLoggedIn();
        verify(userService).getUser(loadUserCallbackArgumentCaptor.capture());
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(view).navigateToHomeUi();
    }

}