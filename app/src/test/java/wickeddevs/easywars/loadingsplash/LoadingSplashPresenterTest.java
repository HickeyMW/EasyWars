package wickeddevs.easywars.loadingsplash;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import wickeddevs.easywars.data.model.User;
import wickeddevs.easywars.data.service.contract.UserService;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by 375csptssce on 7/26/16.
 */
public class LoadingSplashPresenterTest {

    @Mock
    private LoadingSplashContract.View mLoadingSplashView;

    @Mock
    private UserService mUserService;

    @Captor
    private ArgumentCaptor<UserService.LoadUserCallback> mLoadUserCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<Boolean> mIsAdminArgumentCaptor;

    private LoadingSplashPresenter mLoadingSplashPresenter;

    @Before
    public void setupLoadingSplashPresenter() {
        MockitoAnnotations.initMocks(this);

        mLoadingSplashPresenter = new LoadingSplashPresenter(mLoadingSplashView, mUserService);
    }

    @Test
    public void checkIfLoggedIn_isNotLoggedIn_showLoginUi() {
        when(mUserService.isLoggedIn()).thenReturn(false);
        mLoadingSplashPresenter.start();
        verify(mLoadingSplashView).showLoginUi();
    }

    @Test
    public void checkIfLoggedIn_isLoggedInStateBlank_showNoClanUi() {
        when(mUserService.isLoggedIn()).thenReturn(true);
        mLoadingSplashPresenter.start();
        verify(mUserService).getUser(mLoadUserCallbackArgumentCaptor.capture());
        mLoadUserCallbackArgumentCaptor.getValue().onUserLoaded(new User());
        verify(mLoadingSplashView).showNoClanUi();
    }

    @Test
    public void checkIfLoggedIn_isLoggedInStateCreating_showCreatingClanUi() {
        when(mUserService.isLoggedIn()).thenReturn(true);
        mLoadingSplashPresenter.start();
        verify(mUserService).getUser(mLoadUserCallbackArgumentCaptor.capture());
        User user = new User();
        user.state = User.CREATING;
        mLoadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(mLoadingSplashView).showCreatingClanUi();
    }

    @Test
    public void checkIfLoggedIn_isLoggedInStateJoining_showJoiningClanUi() {
        when(mUserService.isLoggedIn()).thenReturn(true);
        mLoadingSplashPresenter.start();
        verify(mUserService).getUser(mLoadUserCallbackArgumentCaptor.capture());
        User user = new User();
        user.state = User.JOINING;
        mLoadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(mLoadingSplashView).showJoiningClanUi();
    }

    @Test
    public void checkIfLoggedIn_isLoggedInStateMember_showHomeUiMember() {
        when(mUserService.isLoggedIn()).thenReturn(true);
        mLoadingSplashPresenter.start();
        verify(mUserService).getUser(mLoadUserCallbackArgumentCaptor.capture());
        User user = new User();
        user.state = User.MEMBER;
        mLoadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(mLoadingSplashView).showHomeUi(mIsAdminArgumentCaptor.capture());
        assertEquals(mIsAdminArgumentCaptor.getValue(), false);
    }

    @Test
    public void checkIfLoggedIn_isLoggedInStateAdmin_showNoClanUiAdmin() {
        when(mUserService.isLoggedIn()).thenReturn(true);
        mLoadingSplashPresenter.start();
        verify(mUserService).getUser(mLoadUserCallbackArgumentCaptor.capture());
        User user = new User();
        user.state = User.ADMIN;
        mLoadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);
        verify(mLoadingSplashView).showHomeUi(mIsAdminArgumentCaptor.capture());
        assertEquals(mIsAdminArgumentCaptor.getValue(), true);
    }

    @Test
    public void returnedFromLogin_loginSuccessful_navigateOnUserState() {
        mLoadingSplashPresenter.returnedFromLogin(true);
        verify(mUserService).getUser(mLoadUserCallbackArgumentCaptor.capture());
    }

    @Test
    public void returnedFromLogin_loginFailed_navigateOnUserState() {
        mLoadingSplashPresenter.returnedFromLogin(false);
        verify(mLoadingSplashView).showLoginError();
    }
}