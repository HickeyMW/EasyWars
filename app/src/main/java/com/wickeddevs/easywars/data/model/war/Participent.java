package com.wickeddevs.easywars.data.model.war;

import java.util.ArrayList;

/**
 * Created by 375csptssce on 9/9/16.
 */
public class Participent {

    public String key;
    public String uid;
    public String name;
    public int thLevel;
    public ArrayList<Attack> attackClaims = new ArrayList<>();

    public Participent() {
    }

    public Participent(String name) {

        this.name = name;
    }

    public Participent(String uid, String name, int thLevel) {
        this.uid = uid;
        this.name = name;
        this.thLevel = thLevel;
    }
}
