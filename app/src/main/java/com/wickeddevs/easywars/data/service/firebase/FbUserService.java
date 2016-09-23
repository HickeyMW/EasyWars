package com.wickeddevs.easywars.data.service.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.wickeddevs.easywars.data.model.User;
import com.wickeddevs.easywars.data.service.contract.UserService;

/**
 * Created by 375csptssce on 8/9/16.
 */
public class FbUserService implements UserService {

    @Override
    public boolean isLoggedIn() {
        return (FirebaseAuth.getInstance().getCurrentUser() != null);
    }

    @Override
    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void getUser(final LoadUserCallback callback) {
        FbInfo.getUserRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user == null) {
                    user = new User();
                }
                callback.onUserLoaded(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void listenOnUser(final LoadUserCallback callback) {
        FbInfo.getUserRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (callback != null){
                    callback.onUserLoaded(user);
                } else {
                    FbInfo.getUserRef().removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean isMyId(String uid) {
        return uid.equals(FbInfo.getUid());
    }

    @Override
    public void setAdmin(boolean isAdmin) {
        if (isAdmin) {
            FbInfo.setState(User.STATE_ADMIN);
        } else {
            FbInfo.setState(User.STATE_MEMBER);
        }
    }
}
