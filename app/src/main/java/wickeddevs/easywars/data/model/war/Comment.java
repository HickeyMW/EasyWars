package wickeddevs.easywars.data.model.war;

/**
 * Created by 375csptssce on 8/16/16.
 */
public class Comment {

    public String uid;
    public String body;
    public String name;
    public long timestamp;
    public String dateTime;

    public Comment(String uid, String body, long timestamp) {

        this.uid = uid;
        this.body = body;
        this.timestamp = timestamp;
    }

    public Comment() {
    }
}
