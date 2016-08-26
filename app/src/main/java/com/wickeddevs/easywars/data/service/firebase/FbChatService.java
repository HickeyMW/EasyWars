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
public class FbChatService implements ChatService {
    private ChildEventListener memberChildListener;
    private MessageListener memberMessageListener;
    private long memberMessagesToIgnore = -1;
    private ChildEventListener adminChildListener;
    private MessageListener adminMessageListener;
    private long adminMessagesToIgnore = -1;

    public FbChatService() {
        memberChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (memberMessagesToIgnore == 0) {
                    Message message = dataSnapshot.getValue(Message.class);
                    message.isSentMessage = (message.uid.equals(FbInfo.getUid()));
                    message.key = dataSnapshot.getKey();
                    memberMessageListener.newMessage(message);
                } else {
                    memberMessagesToIgnore--;
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
        };
        adminChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (adminMessagesToIgnore == 0) {
                    Message message = dataSnapshot.getValue(Message.class);
                    message.isSentMessage = (message.uid.equals(FbInfo.getUid()));
                    message.key = dataSnapshot.getKey();
                    adminMessageListener.newMessage(message);
                } else {
                    adminMessagesToIgnore--;
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
        };
    }

    @Override
    public void setMemberMessageListener(final MessageListener listener) {
        memberMessageListener = listener;
        removeMemberMessageListener();

        FbInfo.getMemberChatRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(final DatabaseReference dbRef) {
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        memberMessagesToIgnore = dataSnapshot.getChildrenCount();
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
                        dbRef.addChildEventListener(memberChildListener);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public void removeMemberMessageListener() {
        FbInfo.getMemberChatRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.removeEventListener(memberChildListener);
            }
        });
    }

    @Override
    public void sendMemberMessage(String body) {
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("body", body);
        hashMap.put("uid", FbInfo.getUid());
        hashMap.put("timestamp", ServerValue.TIMESTAMP);
        FbInfo.getMemberChatRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.push().setValue(hashMap);
            }
        });
    }

    @Override
    public void setAdminMessageListener(final MessageListener listener) {
        adminMessageListener = listener;
        removeAdminMessageListener();

        FbInfo.getAdminChatRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(final DatabaseReference dbRef) {
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        adminMessagesToIgnore = dataSnapshot.getChildrenCount();
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
                        dbRef.addChildEventListener(adminChildListener);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public void removeAdminMessageListener() {
        FbInfo.getAdminChatRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.removeEventListener(adminChildListener);
            }
        });
    }

    @Override
    public void sendAdminMessage(String body) {
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("body", body);
        hashMap.put("uid", FbInfo.getUid());
        hashMap.put("timestamp", ServerValue.TIMESTAMP);
        FbInfo.getAdminChatRef(new FbInfo.DbRefCallback() {
            @Override
            public void onLoaded(DatabaseReference dbRef) {
                dbRef.push().setValue(hashMap);
            }
        });
    }
}
