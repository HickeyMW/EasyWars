package com.wickeddevs.easywars.data.model;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class Member {

    public String name;
    public boolean admin;
    public int thLevel;
    public String uid;

    public Member(String name, boolean admin) {
        this.admin = admin;
        this.name = name;
    }

    public Member(String name, boolean admin, int thLevel, String uid) {
        this.name = name;
        this.admin = admin;
        this.thLevel = thLevel;
        this.uid = uid;
    }

    public Member() {
    }
}
