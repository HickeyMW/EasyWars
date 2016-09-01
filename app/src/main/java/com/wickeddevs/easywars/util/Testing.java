package com.wickeddevs.easywars.util;

import com.wickeddevs.easywars.data.model.api.ApiClan;
import com.wickeddevs.easywars.data.model.api.ApiMember;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by 375csptssce on 9/1/16.
 */
public class Testing {

    public static ArrayList<String> randomClanTagList() {
        int size = randomInt(3, 5);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            strings.add("Tag " + randomString());
        }
        return strings;
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

    public static ApiClan randomApiClan() {
        String randomName = "Name " + UUID.randomUUID().toString();
        String randomTag = "Tag " + UUID.randomUUID().toString();
        int memberCount = randomInt(3, 10);
        ArrayList<ApiMember> memberList = new ArrayList<>();
        for (int i = 0; i < memberCount; i++) {
            memberList.add(randomApiMember());
        }
        return new ApiClan(randomName, randomTag, memberCount, memberList);
    }

    public static ApiClan randomApiClan(String clanTag) {
        String randomName = "Name " + UUID.randomUUID().toString();
        String randomTag = "Tag " + UUID.randomUUID().toString();
        int memberCount = randomInt(3, 10);
        ArrayList<ApiMember> memberList = new ArrayList<>();
        for (int i = 0; i < memberCount; i++) {
            memberList.add(randomApiMember());
        }
        return new ApiClan(randomName, randomTag, memberCount, memberList);
    }

    public static ApiMember randomApiMember() {
        String randomName = "Name " + UUID.randomUUID().toString();
        String randomTag = "Tag " + UUID.randomUUID().toString();
        return new ApiMember(randomName, randomTag);
    }

    public static int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static String randomString() {
        return UUID.randomUUID().toString();
    }
}
