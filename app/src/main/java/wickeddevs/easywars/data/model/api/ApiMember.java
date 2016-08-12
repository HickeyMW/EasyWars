package wickeddevs.easywars.data.model.api;

/**
 * Created by hicke_000 on 8/5/2016.
 */
public class ApiMember {

    public String name;
    public String tag;
    public int clanRank;
    public int donationsReceived;
    public int donations;
    public int expLevel;
    public Object league;
    public int previousClanRank;
    public String role;
    public int trophies;

    public ApiMember(String name, String tag) {
        this.name = name;
        this.tag = tag;
    }


}
