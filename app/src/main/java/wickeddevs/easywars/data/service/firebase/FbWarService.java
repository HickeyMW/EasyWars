package wickeddevs.easywars.data.service.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import wickeddevs.easywars.data.model.war.Base;
import wickeddevs.easywars.data.model.war.Comment;
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
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        if (iterator.hasNext()) {
                            DataSnapshot ds = iterator.next();
                            War war = ds.getValue(War.class);
                            war.key = ds.getKey();
                            callback.onLoaded(war);
                        } else {
                            callback.onLoaded(null);
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
    public void startWar(final War war) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                DatabaseReference warRef = dbRef.push();
                warRef.child("startTime").setValue(war.startTime);
                warRef.child("enemyName").setValue(war.enemyName);
                warRef.child("enemyTag").setValue(war.enemyTag);
                warRef.child("bases").setValue(war.bases);
            }
        });
    }

    @Override
    public void loadBase(final String warId, final String baseId, final LoadBaseCallback callback) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.child(warId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Base base = dataSnapshot.child("bases/" + baseId).getValue(Base.class);
                        DataSnapshot ds = dataSnapshot.child("claims/" + baseId);
                        if (ds.hasChild(FbInfo.getUid())) {
                            base.didClaim = true;
                        }
                        Iterator<DataSnapshot> iterator = ds.getChildren().iterator();
                        ArrayList<String> claims = new ArrayList<>();
                        while (iterator.hasNext()) {
                            claims.add(iterator.next().getKey());
                        }

                        Iterator<DataSnapshot> iter = dataSnapshot.child("comments/" + baseId).getChildren().iterator();
                        ArrayList<Comment> comments = new ArrayList<>();
                        while (iter.hasNext()) {
                            comments.add(iter.next().getValue(Comment.class));
                        }
                        base.claims = claims;
                        base.comments = comments;
                        callback.onLoaded(base);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public void claimBase(final String warId, final String baseId) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.child(warId + "/claims/" + baseId + "/" + FbInfo.getUid()).setValue(0);

            }
        });
    }

    @Override
    public void removeClaim(final String warId, final String baseId) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.child(warId + "/claims/" + baseId + "/" + FbInfo.getUid()).removeValue();

            }
        });
    }

    @Override
    public void addComment(final String body, final String warId, final String baseId) {
        FbInfo.getWarRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                Comment comment = new Comment(FbInfo.getUid(), body, System.currentTimeMillis());
                dbRef.child(warId + "/comments/" + baseId).push().setValue(comment);
            }
        });
    }

}
