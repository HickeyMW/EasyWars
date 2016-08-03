package wickeddevs.easywars.data.service.contract;

/**
 * Created by hicke_000 on 8/2/2016.
 */
public interface StateService {

    public static final int STATE_LOADING = -1;
    public static final int STATE_BLANK = 0;
    public static final int STATE_CREATING = 1;
    public static final int STATE_JOINING = 2;
    public static final int STATE_MEMBER = 3;
    public static final int STATE_ADMIN = 4;

    boolean isLoggedIn();

    void setState(int state);

    int getState();

    void setClanTag(String clanTag);

    String getClanTag();

    String getNoHashClanTag();
}
