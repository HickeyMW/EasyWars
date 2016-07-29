package wickeddevs.easywars.data.service.contract;

import wickeddevs.easywars.data.model.CreateRequest;

/**
 * Created by 375csptssce on 7/28/16.
 */
public interface CreateClanService {

    interface CreateRequestCallback {

        void onCreateRequestLoaded(CreateRequest createRequest);
    }

    interface VerifyCreateCallback {

        void onVerificationLoaded(boolean isVerified);
    }

    void getCreateRequest(CreateRequestCallback callback);

    void deleteCreateRequest();

    void setCreateRequest(String username);

    void verifyCreateRequest(VerifyCreateCallback callback);
}
