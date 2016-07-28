package wickeddevs.easywars.noclan.create;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import wickeddevs.easywars.data.Services;
import wickeddevs.easywars.data.model.User;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class CreateClanPresenterTest {

    @Mock
    private Services.UserService mUserService;

    @Mock
    private CreateClanContract.View mCreateClanView;

}