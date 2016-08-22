package wickeddevs.easywars.data.service.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import wickeddevs.easywars.data.model.war.War;
import wickeddevs.easywars.data.service.contract.WarService;

/**
 * Created by 375csptssce on 8/18/16.
 */
public class FbWarService implements WarService{


    @Override
    public void getLatestWar(final LoadWarCallback callback) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        War war = dataSnapshot.getValue(War.class);
                        if (war == null) {
                            callback.onLoaded(null);
                        }
                        //some other stuff
                        callback.onLoaded(war);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

}
