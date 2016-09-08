package com.wickeddevs.easywars.data.model.war;

import com.google.firebase.database.ServerValue;
import com.wickeddevs.easywars.data.service.firebase.FbInfo;

import java.util.HashMap;

/**
 * Created by 375csptssce on 8/25/16.
 */
public class Attack {

    public String key;
    public String uid;
    public String name;
    public int base = -1;
    public int stars = -2;
    public long timestamp;

    public Attack() {
    }

    public Attack(String uid, int base) {
        this.uid = uid;
        this.base = base;
        this.stars = -1;
    }

    public static HashMap<String, Object> createAttackClaimHashMap(int base) {
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("stars", -1);
        hashMap.put("base", base);
        hashMap.put("timestamp", ServerValue.TIMESTAMP);
        return  hashMap;
    }
}
