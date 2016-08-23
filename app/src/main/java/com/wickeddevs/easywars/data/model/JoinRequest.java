package com.wickeddevs.easywars.data.model;

/**
 * Created by 375csptssce on 7/28/16.
 */
public class JoinRequest {

    public String name;
    public String message;
    public String id;


    public JoinRequest() {
    }

    public JoinRequest(String name, String message) {
        this.name = name;
        this.message = message;
    }
}
