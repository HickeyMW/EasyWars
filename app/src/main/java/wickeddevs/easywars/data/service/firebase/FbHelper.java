package wickeddevs.easywars.data.service.firebase;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import wickeddevs.easywars.data.model.User;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class FbHelper {

    final static String TAG = "FbHelper";

    public static FirebaseDatabase getDb() {
        return FirebaseDatabase.getInstance();
    }

    public static String getUid() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null){
            return currentUser.getUid();
        }
        return null;
    }

    public static DatabaseReference getRequestRef() {
        return getDb().getReference("server/request/" + getUid());
    }

    public static DatabaseReference getResponseRef() {
        return getDb().getReference("server/response/" + getUid());
    }
}
