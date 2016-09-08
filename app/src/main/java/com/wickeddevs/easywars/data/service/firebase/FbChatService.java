package com.wickeddevs.easywars.data.service.firebase;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import com.wickeddevs.easywars.data.model.Message;
import com.wickeddevs.easywars.data.service.contract.ChatService;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class FbChatService implements ChatService, ValueEventListener, ChildEventListener {

    final static String TAG = "FbChatService";

    private DatabaseReference messageRef;
    private MessageListener messageListener;

    @Override
    public void setMessageListener(boolean isAdmin, final MessageListener listener) {
        messageListener = listener;
        if (isAdmin) {
            FbInfo.getAdminChatRef(new FbInfo.DbRefCallback() {
                @Override
                public void onLoaded(DatabaseReference dbRef) {
                    messageRef = dbRef;
                    dbRef.addChildEventListener(FbChatService.this);
                    dbRef.addListenerForSingleValueEvent(FbChatService.this);
                }
            });
        } else {
            FbInfo.getMemberChatRef(new FbInfo.DbRefCallback() {
                @Override
                public void onLoaded(DatabaseReference dbRef) {
                    messageRef = dbRef;
                    dbRef.addChildEventListener(FbChatService.this);
                    dbRef.addListenerForSingleValueEvent(FbChatService.this);
                }
            });
        }
    }

    @Override
    public void removeMessageListener() {
        messageRef.removeEventListener(((ChildEventListener) this));
    }

    @Override
    public void sendMessage(boolean isAdmin, String body) {
        final HashMap<String, Object> hashMap = Message.createMessageHashMap(FbInfo.getUid(), body);
        if (isAdmin) {
            FbInfo.getAdminChatRef(new FbInfo.DbRefCallback() {
                @Override
                public void onLoaded(DatabaseReference dbRef) {
                    dbRef.push().setValue(hashMap);
                }
            });
        } else {
            FbInfo.getMemberChatRef(new FbInfo.DbRefCallback() {
                @Override
                public void onLoaded(DatabaseReference dbRef) {
                    dbRef.push().setValue(hashMap);
                }
            });
        }
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (messageRef == null) {
            Log.e(TAG, "onChildAdded: Listener is null while trying to load messages");
        } else {
            Message message = dataSnapshot.getValue(Message.class);
            message.isSentMessage = (message.uid.equals(FbInfo.getUid()));
            message.key = dataSnapshot.getKey();
            messageListener.newMessage(message);
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        messageListener.initialLoadComplete();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
