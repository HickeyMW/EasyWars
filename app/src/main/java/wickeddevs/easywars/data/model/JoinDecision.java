package wickeddevs.easywars.data.model;

/**
 * Created by 375csptssce on 7/28/16.
 */
public class JoinDecision {

    public final static int APPROVED = 1;
    public final static int PENDING = 0;
    public final static int DENIED = -1;

    public int state = PENDING;

    public JoinDecision() {
    }

    public JoinDecision(boolean state) {
        if (state) {
            this.state = APPROVED;
        } else {
            this.state = DENIED;
        }
    }
}
