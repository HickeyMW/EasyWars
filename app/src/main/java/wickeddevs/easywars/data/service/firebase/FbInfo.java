package wickeddevs.easywars.data.service.firebase;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import wickeddevs.easywars.data.service.contract.StateService;

/**
 * Created by hicke_000 on 8/2/2016.
 */
public enum FbInfo implements StateService {
    INSTANCE;

    final static String TAG = "FbInfo";

    private int state = STATE_LOADING;
    private String clanTag = null;
    private int test = 0;

    FbInfo() {
        getDb().getReference("users/" + getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("state")) {
                    state = dataSnapshot.child("state").getValue(Integer.class);
                } else {
                    setState(STATE_BLANK);
                }
                clanTag = dataSnapshot.child("clanTag").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void setState(int state) {
        if (getUid() != null) {
            getDb().getReference("users/" + getUid() + "/state").setValue(state);
        } else {
            Log.e(TAG, "Tried to set state while uid is null");
        }

    }

    @Override
    public boolean isLoggedIn() {
        return (FirebaseAuth.getInstance().getCurrentUser() != null);
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void setClanTag(String clanTag) {
        if (getUid() != null) {
            getDb().getReference("users/" + getUid() + "/clanTag").setValue(clanTag);
        } else {
            Log.e(TAG, "Tried to set clanTag while uid is null");
        }

    }

    @Override
    public String getClanTag() {
        return clanTag;
    }

    @Override
    public String getNoHashClanTag() {
        if (clanTag != null) {
            return clanTag.substring(1);
        }
        return null;
    }

    public FirebaseDatabase getDb() {
        return FirebaseDatabase.getInstance();
    }

    public String getUid() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null){
            return currentUser.getUid();
        }
        return null;
    }

    public DatabaseReference getRequestRef() {
        return getDb().getReference("server/request/" + getUid());
    }

    public DatabaseReference getResponseRef() {
        return getDb().getReference("server/response/" + getUid());
    }
}
