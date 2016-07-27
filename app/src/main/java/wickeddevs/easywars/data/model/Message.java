package wickeddevs.easywars.data.model;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class Message {

    public String uid;
    public String body;
    public long timestamp;

    public Message(String uid, String body, long timestamp) {
        this.uid = uid;
        this.body = body;
        this.timestamp = timestamp;
    }

    public Message(String uid, String message) {
        this.uid = uid;
        this.body = message;
    }

    public Message() {

    }
}
