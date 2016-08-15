package wickeddevs.easywars.data.model;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class Member {

    public String name;
    public boolean admin;
    public String uid;

    public Member(String name, boolean admin) {
        this.admin = admin;
        this.name = name;
    }

    public Member(String name, boolean admin, String uid) {
        this.admin = admin;
        this.name = name;

        this.uid = uid;
    }

    public Member() {
    }
}
