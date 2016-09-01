package com.wickeddevs.easywars.data.service.firebase;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.wickeddevs.easywars.data.model.User;

/**
 * Created by hicke_000 on 8/2/2016.
 */
public enum FbInfo {
    INSTANCE;

    final static String TAG = "FbInfo";

    private FbInfo() {
    }

    public static void syncUser() {

        getUserRef().keepSynced(true);
    }

    public static void setUser(final User user) {
        if (getUid() != null) {
            getDb().getReference("users/" + getUid()).setValue(user);
            getClanRef(new DbRefCallback() {
                @Override
                public void onLoaded(DatabaseReference dbRef) {
                    dbRef.keepSynced(true);
                }
            });
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

    public static void setClanTag(final String clanTag) {
        if (getUid() != null) {
            getUserRef().child("clanTag").setValue(clanTag);
            getClanRef(new DbRefCallback() {
                @Override
                public void onLoaded(DatabaseReference dbRef) {
                    dbRef.keepSynced(true);
                }
            });
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

    public static DatabaseReference getAllClansRef() {
        return FbInfo.getDb().getReference("clans/");
    }

    public static DatabaseReference getUserRef() {
        return FbInfo.getDb().getReference("users/" + getUid());
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

    public static DatabaseReference getVersionRef() {
        return getDb().getReference("version/");
    }

    public static void getMemberChatRef(final DbRefCallback callback) {
        getClanTagNoHash(new ClanTagCallBack() {
            @Override
            public void onLoaded(String clanTag) {
                callback.onLoaded(getDb().getReference("messages/" + clanTag + "/member"));
            }
        });
    }

    public static void getAdminChatRef(final DbRefCallback callback) {
        getClanTagNoHash(new ClanTagCallBack() {
            @Override
            public void onLoaded(String clanTag) {
                callback.onLoaded(getDb().getReference("messages/" + clanTag + "/admin"));
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

    public static void getWarRef(final DbRefCallback callback) {
        getClanTagNoHash(new ClanTagCallBack() {
            @Override
            public void onLoaded(String clanTag) {
                callback.onLoaded(getDb().getReference("wars/" + clanTag));
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
