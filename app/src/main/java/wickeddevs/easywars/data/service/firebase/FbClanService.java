package wickeddevs.easywars.data.service.firebase;

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


    private DatabaseReference getClanRef() {
        return FbInfo.INSTANCE.getDb().getReference("clans/" + FbInfo.INSTANCE.getNoHashClanTag());
    }

    @Override
    public void getMember(final String uid, final LoadMemberCallback callback) {
        getClanRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Clan clan = dataSnapshot.getValue(Clan.class);
                //TODO: Contains cause could crash
                Member member = clan.members.get(uid);
                member.uid = uid;
                callback.onMemberLoaded(member);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getClan(final LoadClanCallback callback) {

        getClanRef().addListenerForSingleValueEvent(new ValueEventListener() {
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

    @Override
    public void checkIfAdmin(final AdminCheckCallback callback) {
        getClan(new LoadClanCallback() {
            @Override
            public void onClanLoaded(Clan clan) {
                Member member = clan.members.get(FbInfo.INSTANCE.getUid());
                callback.onLoaded(member.admin);
            }
        });
    }
}
