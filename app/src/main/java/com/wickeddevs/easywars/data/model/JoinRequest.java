package com.wickeddevs.easywars.data.model;

/**
 * Created by 375csptssce on 7/28/16.
 */
public class JoinRequest {

    public String name;
    public String message;
    public int thLevel;
    public String key;


    public JoinRequest() {
    }

    public JoinRequest(String name, String message, int thLevel) {
        this.name = name;
        this.message = message;
        this.thLevel = thLevel;
    }
}
