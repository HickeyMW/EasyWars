package com.wickeddevs.easywars.data.model;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class Message {

    public String key;
    public String uid;
    public String body;
    public long timestamp;
    public String name;
    public String dateTime;
    public boolean isSentMessage;

    public static HashMap<String, Object> createMessageHashMap(String uid, String body) {
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("body", body);
        hashMap.put("timestamp", ServerValue.TIMESTAMP);
        return  hashMap;
    }

    public Message(String uid, String message) {
        this.uid = uid;
        this.body = message;
    }

    public Message() {

    }
}
