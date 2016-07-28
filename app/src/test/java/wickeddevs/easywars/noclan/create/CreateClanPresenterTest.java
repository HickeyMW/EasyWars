package wickeddevs.easywars.noclan.create;

import org.mockito.Mock;

import wickeddevs.easywars.data.service.contract.UserService;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class CreateClanPresenterTest {

    @Mock
    private UserService mUserService;

    @Mock
    private CreateClanContract.View mCreateClanView;

}