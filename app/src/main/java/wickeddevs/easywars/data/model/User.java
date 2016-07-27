package wickeddevs.easywars.data.model;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class User {

    public static final int BLANK = 0;
    public static final int CREATING = 1;
    public static final int JOINING = 2;
    public static final int MEMBER = 3;
    public static final int ADMIN = 4;

    public int state = BLANK;
    public String clanTag;

    public User() {
    }

    public User(int state, String clanTag) {
        this.state = state;
        this.clanTag = clanTag;
    }
}
