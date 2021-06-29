package dm.relationship.appServers.thirdPartyServer;

import ws.common.utils.message.implement.AbstractInnerMsg;
import drama.protos.EnumsProtos.PlatformTypeEnum;


public class In_LoginToPlatformRequest extends AbstractInnerMsg {
    private static final long serialVersionUID = 1L;

    private PlatformTypeEnum PlatformTypeEnum;
    private String uid; // 第三方的唯一标识，第三方返回
    private String sessionOrToken;// 登录的session或者Token，第三方返回
    private String args;


    public In_LoginToPlatformRequest(PlatformTypeEnum platformType, String uid, String sessionOrToken, String args) {
        this.PlatformTypeEnum = platformType;
        this.uid = uid;
        this.sessionOrToken = sessionOrToken;
        this.args = args;
    }

    public PlatformTypeEnum getPlatformType() {
        return PlatformTypeEnum;
    }

    public String getUid() {
        return uid;
    }

    public String getSessionOrToken() {
        return sessionOrToken;
    }

    public String getArgs() {
        return args;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("PlatformTypeEnum=[").append(PlatformTypeEnum).append("]\n");
        sb.append("uid=[").append(uid).append("]\n");
        sb.append("sessionOrToken=[").append(sessionOrToken).append("]\n");
        sb.append("args=[").append(args).append("]\n");
        return sb.toString();
    }
}