package dm.relationship.base.msg.player;

import dm.relationship.base.resultCode.ResultCodeEnum;
import ws.common.utils.message.implement.AbstractInnerMsg;

/**
 * Created by lee on 17-5-24.
 */
public class In_ReGetCenterPlayer {

    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = -8107050011204431040L;
        private String simpleId;

        public Request(String simpleId) {
            this.simpleId = simpleId;
        }

        public String getSimpleId() {
            return simpleId;
        }
    }


    public static class Response extends AbstractInnerMsg {
        private static final long serialVersionUID = 1138643558463740808L;

        public Response() {
            this.setResultCode(ResultCodeEnum.SUCCESS);
        }
    }
}
