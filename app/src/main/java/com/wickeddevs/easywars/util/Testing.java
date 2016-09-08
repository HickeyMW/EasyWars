package com.wickeddevs.easywars.util;

import com.wickeddevs.easywars.data.model.CreateRequest;
import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.model.Message;
import com.wickeddevs.easywars.data.model.User;
import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.data.model.api.ApiMember;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by 375csptssce on 9/1/16.
 */
public class Testing {

    //////////////////// ApiClan ////////////////////

    public static ApiClan randomApiClan() {
        String randomName = "Name " + randomString();
        String randomTag = "Tag " + randomString();
        int memberCount = randomInt(3, 10);
        ArrayList<ApiMember> memberList = new ArrayList<>();
        for (int i = 0; i < memberCount; i++) {
            memberList.add(randomApiMember());
        }
        return new ApiClan(randomName, randomTag, memberCount, memberList);
    }

    public static ApiClan randomApiClan(String clanTag) {
        String randomName = "Name " + randomString();
        String randomTag = "Tag " + randomString();
        int memberCount = randomInt(3, 10);
        ArrayList<ApiMember> memberList = new ArrayList<>();
        for (int i = 0; i < memberCount; i++) {
            memberList.add(randomApiMember());
        }
        return new ApiClan(randomName, randomTag, memberCount, memberList);
    }

    public static ArrayList<ApiClan> randomApiClanList() {
        int size = randomInt(3, 5);
        ArrayList<ApiClan> apiClans = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            apiClans.add(randomApiClan());
        }
        return apiClans;
    }

    public static ArrayList<ApiClan> randomApiClanList(ArrayList<String> clanTags) {
        ArrayList<ApiClan> apiClans = new ArrayList<>();
        for (int i = 0; i < clanTags.size(); i++) {
            apiClans.add(randomApiClan());
        }
        return apiClans;
    }


    //////////////////// ApiMember ////////////////////

    public static ApiMember randomApiMember() {
        String randomName = "Name " + randomString();
        String randomTag = "Tag " + randomString();
        return new ApiMember(randomName, randomTag);
    }


    //////////////////// Member ////////////////////

    public static CreateRequest randomCreateRequest() {
        String randomName = "Name " + randomString();
        String randomTag = "Tag " + randomString();
        return new CreateRequest(randomName, randomTag);
    }

    //////////////////// Member ////////////////////

    public static Member randomMember(String uid) {
        String name = "name " + randomString();
        boolean admin = randomBoolean();
        return new Member(name, admin, uid);
    }

    //////////////////// Message ////////////////////

    public static Message randomMessage() {
        Message message = new Message();
        message.uid = "uid " + randomString();
        message.body = "body " + randomString();
        message.timestamp = System.currentTimeMillis();
        return message;
    }


    //////////////////// User ////////////////////

    public static User randomUser(int state) {
        String clanTag = "clanTag " + randomString();
        return new User(state, clanTag);
    }


    //////////////////// Other ////////////////////

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static ArrayList<String> randomStringList() {
        int size = randomInt(3, 5);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            strings.add(randomString());
        }
        return strings;
    }

    public static int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static boolean randomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }


}
