package dm.relationship.base.msg.player;

import ws.common.utils.message.implement.AbstractInnerMsg;

/**
 * Created by root on 8/24/16.
 */
public class In_MessageGetPlayerInfoRequest extends AbstractInnerMsg {
    private static final long serialVersionUID = 3663649197411869388L;

    public In_MessageGetPlayerInfoRequest(String id) {
        this.id = id;
    }

    private String id;

    public String getId() {
        return id;
    }
}
