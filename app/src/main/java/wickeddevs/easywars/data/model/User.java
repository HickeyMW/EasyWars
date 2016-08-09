package wickeddevs.easywars.data.model;

/**
 * Created by 375csptssce on 8/9/16.
 */
public class User {

    public static final int STATE_BLANK = 0;
    public static final int STATE_CREATING = 1;
    public static final int STATE_JOINING = 2;
    public static final int STATE_MEMBER = 3;
    public static final int STATE_ADMIN = 4;

    public User(int state, String clanTag) {
        this.state = state;
        this.clanTag = clanTag;
    }

    public User() {

    }

    public int state = STATE_BLANK;
    public String clanTag;
}
