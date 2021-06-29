package dm.relationship.base.msg;

import akka.actor.ActorRef;
import ws.common.utils.message.implement.AbstractInnerMsg;
import ws.common.utils.message.interfaces.InnerMsg;

public class CheckPlayerOnlineMsgRequest<T extends InnerMsg> extends AbstractInnerMsg {
    private static final long serialVersionUID = 723285027922089200L;
    private final String checkPlayerId;
    private final T attachMsg;
    private ActorRef sender;

    public CheckPlayerOnlineMsgRequest(String checkPlayerId, T attachMsg) {
        this.checkPlayerId = checkPlayerId;
        this.attachMsg = attachMsg;
    }

    public void setSender(ActorRef sender) {
        this.sender = sender;
    }

    public String getCheckPlayerId() {
        return checkPlayerId;
    }

    public T getAttachMsg() {
        return attachMsg;
    }

    public ActorRef getSender() {
        return sender;
    }
}
