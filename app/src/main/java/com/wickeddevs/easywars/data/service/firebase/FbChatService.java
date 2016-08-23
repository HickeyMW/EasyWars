package com.wickeddevs.easywars.data.service.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.wickeddevs.easywars.data.model.Message;
import com.wickeddevs.easywars.data.service.contract.ChatService;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class FbChatService implements ChatService, ChildEventListener {

    private MessageListener mMessageListener;
    private long messagesToIgnore = -1;

    @Override
    public void setMessageListener(final MessageListener listener) {
        mMessageListener = listener;

        FbInfo.getChatRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(final DatabaseReference dbRef) {
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        messagesToIgnore = dataSnapshot.getChildrenCount();
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        ArrayList<Message> messages = new ArrayList<>();
                        while (iterator.hasNext()) {
                            DataSnapshot dSnap = iterator.next();
                            Message message = dSnap.getValue(Message.class);
                            message.key = dSnap.getKey();
                            message.isSentMessage = (message.uid.equals(FbInfo.getUid()));
                            messages.add(message);
                        }
                        listener.initialMessages(messages);
                        dbRef.addChildEventListener(FbChatService.this);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public void removeMessageListener() {
        FbInfo.getChatRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.removeEventListener(FbChatService.this);
            }
        });
    }

    @Override
    public void sendMessage(String body) {
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("body", body);
        hashMap.put("uid", FbInfo.getUid());
        hashMap.put("timestamp", ServerValue.TIMESTAMP);
        FbInfo.getChatRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.push().setValue(hashMap);
            }
        });
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (messagesToIgnore == 0) {
            Message message = dataSnapshot.getValue(Message.class);
            message.isSentMessage = (message.uid.equals(FbInfo.getUid()));
            message.key = dataSnapshot.getKey();
            mMessageListener.newMessage(message);
        } else {
            messagesToIgnore--;
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
    public void onCancelled(DatabaseError databaseError) {

    }
}
