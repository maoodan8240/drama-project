package dm.relationship.base.msg;

import com.google.protobuf.Message;
import ws.common.utils.message.implement.AbstractInnerMsg;

public class In_Message extends AbstractInnerMsg {
    private Message message;

    public In_Message(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
