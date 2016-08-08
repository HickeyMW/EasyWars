package wickeddevs.easywars.data.service.contract;

/**
 * Created by hicke_000 on 8/2/2016.
 */
public interface StateService {

    int STATE_LOADING = -1;
    int STATE_BLANK = 0;
    int STATE_CREATING = 1;
    int STATE_JOINING = 2;
    int STATE_MEMBER = 3;
    int STATE_ADMIN = 4;

    boolean isLoggedIn();

    void setState(int state);

    int getState();

    void setClanTag(String clanTag);

    String getClanTag();

    String getNoHashClanTag();
}
