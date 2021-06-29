package dm.relationship.base.msg.chat;

import ws.common.utils.message.implement.AbstractInnerMsg;

/**
 * Created by lee on 17-5-5.
 */
public class SendCacheMsgs {
    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = 9024815235216075538L;
    }


    public static class Response extends AbstractInnerMsg {
        private static final long serialVersionUID = 6670654983207931848L;

    }
}
