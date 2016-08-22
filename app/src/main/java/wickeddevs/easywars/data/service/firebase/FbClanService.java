package wickeddevs.easywars.data.service.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import wickeddevs.easywars.data.model.Clan;
import wickeddevs.easywars.data.model.Member;
import wickeddevs.easywars.data.service.contract.ClanService;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class FbClanService implements ClanService {

    final static String TAG = "FbClanService";

    @Override
    public void getMember(final String uid, final LoadMemberCallback callback) {
        FbInfo.getClanRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Clan clan = dataSnapshot.getValue(Clan.class);
                        if (clan != null) {
                            if (clan.members.containsKey(uid)) {
                                Member member = clan.members.get(uid);
                                member.uid = uid;
                                callback.onMemberLoaded(member);
                            } else {
                                Log.e(TAG, "onDataChange: Member didn't exist in clan");
                            }

                        } else {
                            Log.e(TAG, "onDataChange: Clan was null when trying to parse");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public void getSelf(LoadMemberCallback callback) {
        getMember(FbInfo.getUid(), callback);
    }

    @Override
    public void getClan(final LoadClanCallback callback) {

        FbInfo.getClanRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Clan clan = dataSnapshot.getValue(Clan.class);
                        if (clan != null) {
                            callback.onClanLoaded(clan);
                        } else {
                            Log.e(TAG, "onDataChange: Clan was null when trying to parse");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
