package com.wickeddevs.easywars.data.model.war;

import java.util.ArrayList;

/**
 * Created by 375csptssce on 8/16/16.
 */
public class Base {

    public String key;
    public String name;
    public int townHallLevel;
    public ArrayList<Comment> comments;
    public ArrayList<String> claims;

    public Base(String name, int townHallLevel) {
        this.name = name;
        this.townHallLevel = townHallLevel;
    }

    public Base() {
    }
}
