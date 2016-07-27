package wickeddevs.easywars.data.model;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class Member {

    public boolean admin;
    public String name;
    public String uid;

    public Member(boolean admin, String name, String uid) {
        this.admin = admin;
        this.name = name;
        this.uid = uid;
    }

    public Member() {
    }
}
