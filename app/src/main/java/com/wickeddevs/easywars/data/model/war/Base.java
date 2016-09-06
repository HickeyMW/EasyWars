package com.wickeddevs.easywars.data.model.war;

import java.util.ArrayList;

/**
 * Created by 375csptssce on 8/16/16.
 */
public class Base {

    public String key;
    public int townHallLevel;
    public String name;
    public ArrayList<Comment> comments;
    public ArrayList<String> claims;

    public Base(int townHallLevel, String name) {
        this.townHallLevel = townHallLevel;
        this.name = name;
    }

    public Base() {
    }
}
