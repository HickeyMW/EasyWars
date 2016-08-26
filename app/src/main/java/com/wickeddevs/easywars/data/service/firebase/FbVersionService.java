package com.wickeddevs.easywars.data.service.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.wickeddevs.easywars.data.service.contract.VersionService;

/**
 * Created by 375csptssce on 8/26/16.
 */
public class FbVersionService implements VersionService {

    @Override
    public void getCurrentVersion(final CheckVersionCallback callback) {
        FbInfo.getVersionRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int major = dataSnapshot.child("major").getValue(Integer.class);
                int minor = dataSnapshot.child("minor").getValue(Integer.class);
                callback.onVersionLoaded(major, minor);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
