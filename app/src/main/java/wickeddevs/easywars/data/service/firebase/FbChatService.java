package wickeddevs.easywars.data.service.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import wickeddevs.easywars.data.model.Message;
import wickeddevs.easywars.data.service.contract.ChatService;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class FbChatService implements ChatService, ChildEventListener {

    private MessageListener mMessageListener;
    private long messagesToIgnore = -1;

    @Override
    public void setMessageListener(final MessageListener listener) {
        mMessageListener = listener;


        FbHelper.getChatRef(new FbHelper.ReferenceCallback() {
            @Override
            public void onRefRetrieved(final DatabaseReference reference) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        messagesToIgnore = dataSnapshot.getChildrenCount();
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        ArrayList<Message> messages = new ArrayList<>();
                        while (iterator.hasNext()) {
                            DataSnapshot dSnap = iterator.next();
                            Message message = dSnap.getValue(Message.class);
                            message.key = dSnap.getKey();
                            message.isSentMessage = (message.uid.equals(FbHelper.getUid()));
                            messages.add(message);
                        }
                        listener.initialMessages(messages);
                        reference.addChildEventListener(FbChatService.this);
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
        FbHelper.getChatRef(new FbHelper.ReferenceCallback() {
            @Override
            public void onRefRetrieved(DatabaseReference reference) {
                reference.removeEventListener(FbChatService.this);
            }
        });
    }

    @Override
    public void sendMessage(String body) {
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("body", body);
        hashMap.put("uid", FbHelper.getUid());
        hashMap.put("timestamp", ServerValue.TIMESTAMP);
        FbHelper.getChatRef(new FbHelper.ReferenceCallback() {
            @Override
            public void onRefRetrieved(DatabaseReference reference) {
                reference.push().setValue(hashMap);
            }
        });
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (messagesToIgnore == 0) {
            Message message = dataSnapshot.getValue(Message.class);
            message.isSentMessage = (message.uid.equals(FbHelper.getUid()));
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
