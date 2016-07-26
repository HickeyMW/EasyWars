package wickeddevs.easywars.loadingsplash;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import wickeddevs.easywars.data.Services;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by 375csptssce on 7/26/16.
 */
public class LoadingSplashPresenterTest {

    @Mock
    private Services.User mUserServices;

    @Mock
    private LoadingSplashContract.View mLoadingSplashView;

    @Captor
    private ArgumentCaptor<Services.User.LoggedInCallback> mLoggedInCallbackArgumentCaptor;

    private LoadingSplashPresenter mLoadingSplashPresenter;

    @Before
    public void setupLoadingSplashPresenter() {
        MockitoAnnotations.initMocks(this);

        mLoadingSplashPresenter = new LoadingSplashPresenter(mUserServices, mLoadingSplashView);
    }

    @Test
    public void checkIfLoggedIn_isLoggedIn_showHomeUi() {
        mLoadingSplashPresenter.start();
        verify(mUserServices).isLoggedIn(mLoggedInCallbackArgumentCaptor.capture());
        mLoggedInCallbackArgumentCaptor.getValue().response(true);
        verify(mLoadingSplashView).showHomeUi();
    }

    @Test
    public void checkIfLoggedIn_isNotLoggedIn_showLoginUi() {
        mLoadingSplashPresenter.start();
        verify(mUserServices).isLoggedIn(mLoggedInCallbackArgumentCaptor.capture());
        mLoggedInCallbackArgumentCaptor.getValue().response(false);
        verify(mLoadingSplashView).showLoginUi();
    }

}