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
import wickeddevs.easywars.data.service.contract.UserService;

/**
 * Created by hicke_000 on 8/2/2016.
 */
public enum FbInfo {
    INSTANCE;

    final static String TAG = "FbInfo";

    private FbInfo() {
    }

    public static void syncUserRef() {
        getUserRef().keepSynced(true);
    }

    public static void setUser(User user) {
        if (getUid() != null) {
            getDb().getReference("users/" + getUid()).setValue(user);
        } else {
            Log.e(TAG, "Tried to set user while uid is null");
        }
    }

    public static void setState(int state) {
        if (getUid() != null) {
            getUserRef().child("state").setValue(state);
        } else {
            Log.e(TAG, "Tried to set state while uid is null");
        }
    }

    public static void setClanTag(String clanTag) {
        if (getUid() != null) {
            getUserRef().child("clanTag").setValue(clanTag);
        } else {
            Log.e(TAG, "Tried to set clanTag while uid is null");
        }

    }

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

    public static DatabaseReference getUserRef() {
        return FbInfo.getDb().getReference("users/" + getUid());
    }

    public static DatabaseReference getClanTagsRef() {
        return getDb().getReference("clanTags");
    }

    public static DatabaseReference getRequestRef() {
        return getDb().getReference("server/request/" + getUid());
    }

    public static DatabaseReference getResponseRef() {
        return getDb().getReference("server/response/" + getUid());
    }

    public static DatabaseReference getCreateRequestRef() {
        return getDb().getReference("createRequests/" + getUid());
    }

    public static void getChatRef(final DbRefCallback callback) {
        getClanTagNoHash(new ClanTagCallBack() {
            @Override
            public void onLoaded(String clanTag) {
                callback.onLoaded(getDb().getReference("messages/" + clanTag));
            }
        });
    }

    public static void getClanRef(final DbRefCallback callback) {
        getClanTagNoHash(new ClanTagCallBack() {
            @Override
            public void onLoaded(String clanTag) {
                callback.onLoaded(getDb().getReference("clans/" + clanTag));
            }
        });
    }

    public static void getJoinRequestRef(final DbRefCallback callback) {
        getClanTagNoHash(new ClanTagCallBack() {
            @Override
            public void onLoaded(String clanTag) {
                callback.onLoaded(getDb().getReference("joinRequests/" + clanTag + "/requests/" + getUid()));
            }
        });
    }

    public static void getJoinRequestsRef(final DbRefCallback callback) {
        getClanTagNoHash(new ClanTagCallBack() {
            @Override
            public void onLoaded(String clanTag) {
                callback.onLoaded(getDb().getReference("joinRequests/" + clanTag + "/requests"));
            }
        });
    }

    public static void getJoinDecisionRef(final DbRefCallback callback) {
        getClanTagNoHash(new ClanTagCallBack() {
            @Override
            public void onLoaded(String clanTag) {
                callback.onLoaded(getDb().getReference("joinRequests/" + clanTag + "/decisions/" + getUid()));
            }
        });
    }

    public static void getJoinDecisionsRef(final DbRefCallback callback) {
        getClanTagNoHash(new ClanTagCallBack() {
            @Override
            public void onLoaded(String clanTag) {
                callback.onLoaded(getDb().getReference("joinRequests/" + clanTag + "/decisions"));
            }
        });
    }

    public static void getClanMembersRef(final DbRefCallback callback) {
        getClanTagNoHash(new ClanTagCallBack() {
            @Override
            public void onLoaded(String clanTag) {
                callback.onLoaded(getDb().getReference("clans/" + clanTag + "/members"));
            }
        });
    }


    private static void getClanTagNoHash(final ClanTagCallBack callBack) {
        getDb().getReference("users/" + getUid() + "/clanTag").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String clanTag = dataSnapshot.getValue(String.class);
                if (clanTag != null) {
                    clanTag = clanTag.substring(1);
                    callBack.onLoaded(clanTag);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public interface DbRefCallback {
        void onLoaded(DatabaseReference dbRef);
    }

    private interface ClanTagCallBack {
        void onLoaded(String clanTag);
    }
}
