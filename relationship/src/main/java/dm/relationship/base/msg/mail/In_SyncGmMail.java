package dm.relationship.base.msg.mail;

import ws.common.utils.message.implement.AbstractInnerMsg;
import drama.protos.EnumsProtos.PlatformTypeEnum;
import dm.relationship.base.resultCode.ResultCodeEnum;
import dm.relationship.topLevelPojos.mailCenter.GmMail;

import java.util.List;


public class In_SyncGmMail {

    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = 8104971441930622778L;

        private PlatformTypeEnum platformType;
        private int outerRealmId;
        private String lastGmMailSendTime; // 最后一个Gm邮件的发送时间

        private String createAt;
        private int level;
        private int vipLevel;

        public Request(PlatformTypeEnum platformType, int outerRealmId, String lastGmMailSendTime, String createAt, int level, int vipLevel) {
            this.platformType = platformType;
            this.outerRealmId = outerRealmId;
            this.lastGmMailSendTime = lastGmMailSendTime;
            this.createAt = createAt;
            this.level = level;
            this.vipLevel = vipLevel;
        }

        public int getOuterRealmId() {
            return outerRealmId;
        }


        public String getCreateAt() {
            return createAt;
        }

        public String getLastGmMailSendTime() {
            return lastGmMailSendTime;
        }

        public int getLevel() {
            return level;
        }

        public int getVipLevel() {
            return vipLevel;
        }

        public PlatformTypeEnum getPlatformType() {
            return platformType;
        }
    }


    public static class Response extends AbstractInnerMsg {
        private static final long serialVersionUID = -3793819844104812374L;

        private List<GmMail> gmMailList;
        private Request request;

        public Response(List<GmMail> gmMailList, Request request) {
            this.gmMailList = gmMailList;
            this.request = request;
            this.resultCode = ResultCodeEnum.SUCCESS;
        }

        public List<GmMail> getGmMailList() {
            return gmMailList;
        }

        public Request getRequest() {
            return request;
        }
    }
}
