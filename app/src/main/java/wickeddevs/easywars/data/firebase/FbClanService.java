package wickeddevs.easywars.data.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import wickeddevs.easywars.data.Services;
import wickeddevs.easywars.data.model.Clan;
import wickeddevs.easywars.data.model.Member;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class FbClanService implements Services.ClanService {

    public FbClanService() {
        FbHelper.getClanRef(new FbHelper.ReferenceCallback() {
            @Override
            public void onRefRetrieved(DatabaseReference reference) {
                reference.keepSynced(true);
            }
        });
    }

    @Override
    public void getMember(final String uid, final LoadMemberCallback callback) {
        FbHelper.getClanRef(new FbHelper.ReferenceCallback() {
            @Override
            public void onRefRetrieved(DatabaseReference reference) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Clan clan = dataSnapshot.getValue(Clan.class);
                        Member member = clan.members.get(uid);
                        member.uid = uid;
                        callback.onMemberLoaded(member);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public void getClan(final LoadClanCallback callback) {
        FbHelper.getClanRef(new FbHelper.ReferenceCallback() {
            @Override
            public void onRefRetrieved(DatabaseReference reference) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Clan clan = dataSnapshot.getValue(Clan.class);
                        callback.onClanLoaded(clan);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
