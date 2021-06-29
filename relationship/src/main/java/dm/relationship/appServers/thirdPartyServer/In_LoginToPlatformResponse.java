package dm.relationship.appServers.thirdPartyServer;

import ws.common.utils.message.implement.AbstractInnerMsg;

public class In_LoginToPlatformResponse extends AbstractInnerMsg {
    private static final long serialVersionUID = 1L;
    private In_LoginToPlatformRequest request;
    private boolean rs;
    private String content;

    public In_LoginToPlatformResponse(In_LoginToPlatformRequest request, boolean rs, String content) {
        this.request = request;
        this.rs = rs;
        this.content = content;
    }

    public In_LoginToPlatformRequest getRequest() {
        return request;
    }

    public boolean isRs() {
        return rs;
    }

    public String getContent() {
        return content;
    }
}