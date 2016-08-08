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
    public ArrayList<ApiMember> memberList = new ArrayList<>();

    public ArrayList<String> getMemberNames(){
        ArrayList<String> members = new ArrayList<>();
        for (ApiMember apiMember : memberList) {
            members.add(apiMember.name);
        }
        return members;
    }
}
