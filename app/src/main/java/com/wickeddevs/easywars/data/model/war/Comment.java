package com.wickeddevs.easywars.data.model.war;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;

/**
 * Created by 375csptssce on 8/16/16.
 */
public class Comment {

    public String key;
    public String uid;
    public String body;
    public int base;
    public String name;
    public long timestamp;
    public String dateTime;

    public Comment(String uid, String body, long timestamp) {

        this.uid = uid;
        this.body = body;
        this.timestamp = timestamp;
    }

    public static HashMap<String, Object> createCommentHashMap(String uid, String body, int base) {
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("body", body);
        hashMap.put("base", base);
        hashMap.put("timestamp", ServerValue.TIMESTAMP);
        return  hashMap;
    }

    public Comment() {
    }
}
