package dm.relationship.base.msg.chat;

import ws.common.utils.message.implement.AbstractInnerMsg;

/**
 * Created by lee on 17-5-5.
 */
public abstract class ChatServerInnerMsg extends AbstractInnerMsg {
    private static final long serialVersionUID = 3937822085065821899L;
    protected int innerRealmId;

    public ChatServerInnerMsg(int innerRealmId) {
        this.innerRealmId = innerRealmId;
    }

    public int getInnerRealmId() {
        return innerRealmId;
    }
}
