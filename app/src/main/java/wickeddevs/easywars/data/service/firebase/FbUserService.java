package wickeddevs.easywars.data.service.firebase;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import wickeddevs.easywars.data.model.User;
import wickeddevs.easywars.data.service.contract.UserService;

/**
 * Created by 375csptssce on 7/26/16.
 */
public class FbUserService implements UserService {

    final static String TAG = "UserService";

    public FbUserService() {
        FbHelper.getUserRef().keepSynced(true);
    }

    @Override
    public boolean isLoggedIn() {
        return !(FirebaseAuth.getInstance().getCurrentUser() == null);
    }

    @Override
    public void getUser(final LoadUserCallback callback) {
        FbHelper.getUserRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    callback.onUserLoaded(dataSnapshot.getValue(User.class));
                } else {
                    callback.onUserLoaded(new User());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
    }

    @Override
    public void setUser(User user) {
        FbHelper.getUserRef().setValue(user);
    }

    @Override
    public void setState(final int state) {
        getUser(new LoadUserCallback() {
            @Override
            public void onUserLoaded(User user) {
                user.state = state;
                setUser(user);
            }
        });
    }
}
