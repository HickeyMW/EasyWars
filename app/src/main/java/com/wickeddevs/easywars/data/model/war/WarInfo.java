package com.wickeddevs.easywars.data.model.war;

/**
 * Created by 375csptssce on 9/7/16.
 */
public class WarInfo {

    public String enemyName;
    public String enemyTag;
    public long startTime;

    public WarInfo(String enemyName, String enemyTag, long startTime) {
        this.enemyName = enemyName;
        this.enemyTag = enemyTag;
        this.startTime = startTime;
    }

    public WarInfo() {

    }
}
