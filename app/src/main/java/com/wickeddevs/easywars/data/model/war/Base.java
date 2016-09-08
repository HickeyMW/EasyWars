package com.wickeddevs.easywars.data.model.war;

import java.util.ArrayList;

/**
 * Created by 375csptssce on 8/16/16.
 */
public class Base {

    public String key;
    public String name;
    public int thLevel;
    public ArrayList<Comment> comments;
    public ArrayList<Attack> attacks;

    public Base(String name, int thLevel) {
        this.name = name;
        this.thLevel = thLevel;
    }

    public Base() {
    }
}
