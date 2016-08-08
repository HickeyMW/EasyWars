package wickeddevs.easywars.data.service.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.service.contract.CreateClanService;
import wickeddevs.easywars.data.service.contract.StateService;

/**
 * Created by 375csptssce on 7/28/16.
 */
public class FbCreateClanService implements CreateClanService {

    private DatabaseReference getCreateRequestRef() {
        return FbInfo.INSTANCE.getDb().getReference("createRequests/" + FbInfo.INSTANCE.getUid());
    }

    @Override
    public void getCreateRequest(final CreateRequestCallback callback) {
        getCreateRequestRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CreateRequest createRequest = dataSnapshot.getValue(CreateRequest.class);
                callback.onCreateRequestLoaded(createRequest);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void deleteCreateRequest() {
        getCreateRequestRef().removeValue();
    }

    @Override
    public void setCreateRequest(String username, String clanTag) {
        FbInfo.INSTANCE.getRequestRef().child("createClan").setValue(new CreateRequest(username,
                clanTag, FbInfo.INSTANCE.getUid()));
    }

    @Override
    public void verifyCreateRequest(VerifyCreateCallback callback) {
        FbInfo.INSTANCE.getRequestRef().setValue("verifyCreateClan");

    }
}
