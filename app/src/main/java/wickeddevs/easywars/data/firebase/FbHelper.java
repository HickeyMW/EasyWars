package wickeddevs.easywars.data.firebase;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
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
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static void getClanTag (final ClanTagCallback callback) {
        FbHelper.getUserRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    String clanTag = dataSnapshot.getValue(User.class).clanTag.substring(1);
                    callback.onClanTagReceived(clanTag);
                } else {
                    Log.e(TAG, "Trying to get clan tag with no clan");
                    callback.onClanTagReceived("");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
    }

    public static DatabaseReference getUserRef() {
        return FirebaseDatabase.getInstance().getReference("users/" + getUid());
    }

    public static void getChatRef(final ReferenceCallback callback) {
        getClanTag(new ClanTagCallback() {
            @Override
            public void onClanTagReceived(String clanTag) {
                callback.onRefRetrieved(getDb().getReference("chat/" + clanTag));
            }
        });
    }

    public interface ReferenceCallback {
        void onRefRetrieved(DatabaseReference reference);
    }

    public interface ClanTagCallback {
        void onClanTagReceived(String clanTag);
    }
}
