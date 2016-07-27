package wickeddevs.easywars.data.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

import wickeddevs.easywars.data.Services;
import wickeddevs.easywars.data.model.Message;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class FbChatService implements Services.ChatService, ChildEventListener {

    private MessageListener mMessageListener;

    @Override
    public void setMessageListener(MessageListener listener) {
        mMessageListener = listener;

        FbHelper.getChatRef(new FbHelper.ReferenceCallback() {
            @Override
            public void onRefRetrieved(DatabaseReference reference) {
                reference.addChildEventListener(FbChatService.this);
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
        Message message = dataSnapshot.getValue(Message.class);
        mMessageListener.newMessage(message);
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
