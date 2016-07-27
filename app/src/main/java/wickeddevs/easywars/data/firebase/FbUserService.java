package wickeddevs.easywars.data.firebase;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import wickeddevs.easywars.data.Services;
import wickeddevs.easywars.data.model.User;

/**
 * Created by 375csptssce on 7/26/16.
 */
public class FbUserService implements Services.UserService {

    final static String TAG = "UserService";

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
}
