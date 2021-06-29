package dm.relationship.base.msg.mail;

import ws.common.utils.message.implement.AbstractInnerMsg;
import dm.relationship.base.resultCode.ResultCodeEnum;
import dm.relationship.topLevelPojos.mails.Mail;


public class In_AddGmMail {

    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = 1L;
        private String playerId;  // 个人邮件时生效
        private int outerRealmId; // 个人邮件时生效
        private Mail mail;

        public Request(String playerId, int outerRealmId, Mail mail) {
            this.playerId = playerId;
            this.outerRealmId = outerRealmId;
            this.mail = mail;
        }

        public Mail getMail() {
            return mail;
        }

        public String getPlayerId() {
            return playerId;
        }

        public int getOuterRealmId() {
            return outerRealmId;
        }
    }


    public static class Response extends AbstractInnerMsg {
        private static final long serialVersionUID = 1L;
        private Request request;

        public Response(Request request) {
            this.request = request;
            this.resultCode = ResultCodeEnum.SUCCESS;
        }

        public Request getRequest() {
            return request;
        }
    }
}
