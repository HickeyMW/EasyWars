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
import com.wickeddevs.easywars.util.Testing;

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
    public void attach_getVersion() {
        presenter.onCreate();
        verify(versionService).getCurrentVersion(checkVersionCallbackArgumentCaptor.capture());
    }

    @Test
    public void attach_getVersion_behindMinorVersion() {
        attach_getVersion();
        checkVersionCallbackArgumentCaptor.getValue().onVersionLoaded(MyApplication.MAJOR_VERSION, MyApplication.MINOR_VERSION + 1);
        verify(view).displayBehindMinorVersion();
        presenter.pressedOkMinor();
        verify(userService).isLoggedIn();
    }

    @Test
    public void attach_getVersion_behindMajorVersion() {
        attach_getVersion();
        checkVersionCallbackArgumentCaptor.getValue().onVersionLoaded(MyApplication.MAJOR_VERSION + 1, MyApplication.MINOR_VERSION);
        verify(view).displayBehindMajorVersion();
        presenter.pressedOkMajor();
        verify(view).closeApp();
    }

    @Test
    public void attach_getVersion_upToDate() {
        attach_getVersion();
        checkVersionCallbackArgumentCaptor.getValue().onVersionLoaded(MyApplication.MAJOR_VERSION, MyApplication.MINOR_VERSION);
    }

    @Test
    public void attach_isUpToDate_isNotLoggedIn_NavigateToLogInUi() {
        when(userService.isLoggedIn()).thenReturn(false);
        attach_getVersion_upToDate();
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
    public void attach_isUpToDate_isLoggedIn_getUser() {
        when(userService.isLoggedIn()).thenReturn(true);
        attach_getVersion_upToDate();
        verify(userService).isLoggedIn();
        verify(userService).getUser(loadUserCallbackArgumentCaptor.capture());
    }

    @Test
    public void attach_isUpToDate_isLoggedIn_stateNoClan() {
        User user = new User(User.STATE_BLANK, "");

        attach_isUpToDate_isLoggedIn_getUser();
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(view).navigateToNoClanUi();
    }

    @Test
    public void attach_isUpToDate_isLoggedIn_stateCreating() {
        User user = Testing.randomUser(User.STATE_CREATING);

        attach_isUpToDate_isLoggedIn_getUser();
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(view).navigateToCreatingClanUi();
    }

    @Test
    public void attach_isUpToDate_isLoggedIn_stateJoining() {
        User user = Testing.randomUser(User.STATE_JOINING);

        attach_isUpToDate_isLoggedIn_getUser();
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(view).navigateToJoiningClanUi();
    }

    @Test
    public void attach_isUpToDate_isLoggedIn_stateMember() {
        User user = Testing.randomUser(User.STATE_MEMBER);

        attach_isUpToDate_isLoggedIn_getUser();
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(view).navigateToHomeUi(false);
    }

    @Test
    public void attach_isUpToDate_isLoggedIn_stateAdmin() {
        User user = Testing.randomUser(User.STATE_ADMIN);

        attach_isUpToDate_isLoggedIn_getUser();
        loadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(view).navigateToHomeUi(true);
    }
}