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

import com.wickeddevs.easywars.MyApplication;
import com.wickeddevs.easywars.data.model.User;
import com.wickeddevs.easywars.data.service.contract.UserService;
import com.wickeddevs.easywars.data.service.contract.VersionService;

/**
 * Created by 375csptssce on 7/26/16.
 */
public class LoadingSplashPresenterTest {

    private LoadingSplashPresenter presenter;

    @Mock
    private LoadingSplashContract.View view;

    @Mock
    private UserService userService;

    @Mock
    private VersionService versionService;

    @Captor
    private ArgumentCaptor<UserService.LoadUserCallback> loadUserCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<VersionService.CheckVersionCallback> checkVersionCallbackArgumentCaptor;

    @Before
    public void setupLoadingSplashPresenter() {
        MockitoAnnotations.initMocks(this);
        presenter = new LoadingSplashPresenter(userService, versionService);
        presenter.registerView(view);
    }

    @Test
    public void attach_behindMinorVersion() {
        presenter.onAttach();
        verify(versionService).getCurrentVersion(checkVersionCallbackArgumentCaptor.capture());
        checkVersionCallbackArgumentCaptor.getValue().onVersionLoaded(MyApplication.MAJOR_VERSION, MyApplication.MINOR_VERSION + 1);
        verify(view).displayBehindMinorVersion();
        presenter.pressedOkMinor();
        verify(userService).isLoggedIn();
    }

    @Test
    public void attach_behindMajorVersion() {
        presenter.onAttach();
        verify(versionService).getCurrentVersion(checkVersionCallbackArgumentCaptor.capture());
        checkVersionCallbackArgumentCaptor.getValue().onVersionLoaded(MyApplication.MAJOR_VERSION + 1, MyApplication.MINOR_VERSION);
        verify(view).displayBehindMajorVersion();
        presenter.pressedOkMajor();
        verify(view).closeApp();
    }

    @Test
    public void attach_upToDate() {
        presenter.onAttach();
        verify(versionService).getCurrentVersion(checkVersionCallbackArgumentCaptor.capture());
        checkVersionCallbackArgumentCaptor.getValue().onVersionLoaded(MyApplication.MAJOR_VERSION, MyApplication.MINOR_VERSION);
    }

    @Test
    public void attach_isUpToDate_isNotLoggedIn_NavigateToLogInUi() {
        when(userService.isLoggedIn()).thenReturn(false);
        attach_upToDate();
        verify(userService).isLoggedIn();
        verify(view).navigateToLoginUi();
    }

    @Test
    public void retunedFromLogin_loginSuccessful() {
        presenter.returnedFromLogin(true);
        verify(userService).getUser(loadUserCallbackArgumentCaptor.capture());
    }

    @Test
    public void retunedFromLogin_loginFailed() {
        presenter.returnedFromLogin(false);
        verify(view).displayMessage(anyString());
    }

    @Test
    public void helper_attach_isUpToDate_isLoggedIn() {
        when(userService.isLoggedIn()).thenReturn(true);
        attach_upToDate();
        verify(userService).isLoggedIn();
    }

    @Test
    public void attach_isUpToDate_isLoggedIn_stateNoClan() {
        helper_attach_isUpToDate_isLoggedIn();
        User user = new User(User.STATE_BLANK, "");
        verify(userService).getUser(loadUserCallbackArgumentCaptor.capture());
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(view).navigateToNoClanUi();
    }

    @Test
    public void attach_isUpToDate_isLoggedIn_stateCreating() {
        helper_attach_isUpToDate_isLoggedIn();
        User user = new User(User.STATE_CREATING, "");
        verify(userService).getUser(loadUserCallbackArgumentCaptor.capture());
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(view).navigateToCreatingClanUi();
    }

    @Test
    public void attach_isUpToDate_isLoggedIn_stateJoining() {
        helper_attach_isUpToDate_isLoggedIn();
        User user = new User(User.STATE_JOINING, "");
        verify(userService).getUser(loadUserCallbackArgumentCaptor.capture());
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(view).navigateToJoiningClanUi();
    }

    @Test
    public void attach_isUpToDate_isLoggedIn_stateMember() {
        helper_attach_isUpToDate_isLoggedIn();
        User user = new User(User.STATE_MEMBER, "");
        verify(userService).getUser(loadUserCallbackArgumentCaptor.capture());
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(view).navigateToHomeUi();
    }

    @Test
    public void attach_isUpToDate_isLoggedIn_stateAdmin() {
        helper_attach_isUpToDate_isLoggedIn();
        User user = new User(User.STATE_ADMIN, "");
        verify(userService).getUser(loadUserCallbackArgumentCaptor.capture());
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(view).navigateToHomeUi();
    }
}