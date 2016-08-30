package com.wickeddevs.easywars.data.service.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.data.service.contract.ApiService;

/**
 * Created by 375csptssce on 7/28/16.
 */
public class FbApiService implements ApiService {

    private GenericTypeIndicator<ArrayList<ApiClan>> gtiArrayListClan = new GenericTypeIndicator<ArrayList<ApiClan>>() {};

    @Override
    public void getApiClan(String tag, final LoadApiClanCallback callback) {
        final String noHashClanTag = tag.substring(1);
        FbInfo.getRequestRef().push().child("api/clanInfo").setValue(tag);
        final DatabaseReference clanTagRef = FbInfo.getResponseRef().child("clanInfo/" + noHashClanTag);
        clanTagRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ApiClan apiClan = dataSnapshot.child("clanInfo").getValue(ApiClan.class);
                if (apiClan != null) {
                    if (callback != null) {
                        callback.onApiClanLoaded(apiClan);
                    }
                    clanTagRef.removeEventListener(this);
                    clanTagRef.removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void searchClans(String query, final SearchApiClansCallback callback) {
        FbInfo.getRequestRef().push().child("api/searchClans").setValue(query);
        final DatabaseReference responseRef = FbInfo.getResponseRef();
        responseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("clanSearch")) {
                    ArrayList<ApiClan> apiClan = dataSnapshot.child("clanSearch").getValue(gtiArrayListClan);
                    if (apiClan != null) {
                        if (callback != null) {
                            callback.onApiClansLoaded(apiClan);
                        }
                        responseRef.removeEventListener(this);
                        responseRef.removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
