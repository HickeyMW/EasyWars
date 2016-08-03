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

    public StateService stateService;

    public FbCreateClanService(StateService stateService) {
        this.stateService = stateService;
    }

    private DatabaseReference getCreateRequestRef() {
        return FbHelper.getDb().getReference("createRequests/" + FbHelper.getUid());
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
    public void setCreateRequest(String username) {
        FbHelper.getRequestRef().child("createClan").setValue(new CreateRequest(username,
                stateService.getNoHashClanTag(), FbHelper.getUid()));
    }

    @Override
    public void verifyCreateRequest(VerifyCreateCallback callback) {
        FbHelper.getRequestRef().setValue("verifyCreateClan");

    }
}
