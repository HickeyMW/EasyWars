package com.wickeddevs.easywars.data.model.war;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

/**
 * Created by 375csptssce on 8/25/16.
 */
public class Attack {

    @Exclude
    public String key;
    public String uid;
    @Exclude
    public String name;
    @Exclude
    public String baseName;
    @Exclude
    public int thLevel;
    public int base = -2;
    public int stars = -2;
    public long timestamp;

    public Attack() {
    }

    public Attack(String uid, int base) {
        this.uid = uid;
        this.base = base;
        this.stars = -1;
    }

    public static HashMap<String, Object> createAttackClaimHashMap(String uid, int base) {
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("stars", -1);
        hashMap.put("base", base);
        hashMap.put("uid", uid);
        hashMap.put("timestamp", ServerValue.TIMESTAMP);
        return  hashMap;
    }
}
