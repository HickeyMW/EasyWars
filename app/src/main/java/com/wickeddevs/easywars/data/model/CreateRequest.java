package com.wickeddevs.easywars.data.model;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class CreateRequest {

    public String username;
    public String tag;
    public String uid;
    public long verification;

    public CreateRequest(String username, String tag) {
        this.username = username;
        this.tag = tag;
    }


    public CreateRequest() {
    }
}
