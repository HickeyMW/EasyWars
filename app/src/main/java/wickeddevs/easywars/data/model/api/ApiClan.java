package wickeddevs.easywars.data.model.api;

import java.util.ArrayList;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class ApiClan {
    public String name;
    public String tag;
    public BadgeUrls badgeUrls;
    public int members;
    public String description;
    public String type;
    public String warFrequency;
    public int clanLevel;
    public int clanPoints;
    public boolean isWarLogPublic;
    public int requiredTrophies;
    public int warLosses;
    public int warTies;
    public int warWinStreak;
    public int warWins;
    public ArrayList<ApiMember> memberList = new ArrayList<>();

    public ArrayList<String> getMemberNames(){
        ArrayList<String> members = new ArrayList<>();
        for (ApiMember apiMember : memberList) {
            members.add(apiMember.name);
        }
        return members;
    }
}
