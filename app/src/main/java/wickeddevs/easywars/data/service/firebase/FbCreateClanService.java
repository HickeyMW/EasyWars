package wickeddevs.easywars.data.service.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.service.contract.CreateClanService;

/**
 * Created by 375csptssce on 7/28/16.
 */
public class FbCreateClanService implements CreateClanService {

    @Override
    public void getCreateRequest(final CreateRequestCallback callback) {
        FbHelper.getCreateRequestRef().addListenerForSingleValueEvent(new ValueEventListener() {
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
        FbHelper.getCreateRequestRef().removeValue();
    }

    @Override
    public void setCreateRequest(CreateRequest createRequest) {
        FbHelper.getRequestRef().child("createClan").setValue(createRequest);
    }

    @Override
    public void verifyCreateRequest(VerifyCreateCallback callback) {
        FbHelper.getRequestRef().setValue("verifyCreateClan");

    }
}
