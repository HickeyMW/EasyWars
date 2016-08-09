package wickeddevs.easywars.data.service.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.model.User;
import wickeddevs.easywars.data.service.contract.CreateClanService;

/**
 * Created by 375csptssce on 7/28/16.
 */
public class FbCreateClanService implements CreateClanService {

    final static String TAG = "FbInfo";

    @Override
    public void getCreateRequest(final CreateRequestCallback callback) {
        FbInfo.getCreateRequestRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CreateRequest createRequest = dataSnapshot.getValue(CreateRequest.class);
                if (createRequest != null) {
                    callback.onCreateRequestLoaded(createRequest);
                    FbInfo.getCreateRequestRef().removeEventListener(this);
                } else {
                    Log.e(TAG, "onDataChange: CreateRequest was null when trying to retrieve");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void deleteCreateRequest() {
        FbInfo.getCreateRequestRef().removeValue();
        FbInfo.setState(User.STATE_BLANK);
    }

    @Override
    public void setCreateRequest(String username, String clanTag) {
        FbInfo.getRequestRef().child("createClan").setValue(new CreateRequest(username, clanTag));
        FbInfo.setState(User.STATE_CREATING);
        FbInfo.setClanTag(clanTag);
    }

    @Override
    public void verifyCreateRequest(final VerifyCreateCallback callback) {
        FbInfo.getRequestRef().setValue("verifyCreateClan");
        FbInfo.getResponseRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("createVerification")) {
                    boolean isVerified = dataSnapshot.child("createVerification").getValue(Boolean.class);
                    callback.onVerificationLoaded(isVerified);
                    dataSnapshot.getRef().removeValue();
                    FbInfo.getResponseRef().removeEventListener(this);
                    if (isVerified) {
                        FbInfo.setState(User.STATE_ADMIN);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
