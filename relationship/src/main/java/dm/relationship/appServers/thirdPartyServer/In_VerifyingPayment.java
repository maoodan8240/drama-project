package dm.relationship.appServers.thirdPartyServer;

import ws.common.utils.message.implement.AbstractInnerMsg;
import drama.protos.EnumsProtos;
import drama.protos.EnumsProtos.PlatformTypeEnum;

public class In_VerifyingPayment {
    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = 567148756904319296L;
        private PlatformTypeEnum platformType;
        private String receipt;
        private String thirdPartyOrderId;
        private String args;

        public Request(EnumsProtos.PlatformTypeEnum platformType, String receipt, String thirdPartyOrderId, String args) {
            this.platformType = platformType;
            this.receipt = receipt;
            this.thirdPartyOrderId = thirdPartyOrderId;
            this.args = args;
        }

        public EnumsProtos.PlatformTypeEnum getPlatformType() {
            return platformType;
        }

        public String getReceipt() {
            return receipt;
        }

        public String getThirdPartyOrderId() {
            return thirdPartyOrderId;
        }

        public String getArgs() {
            return args;
        }
    }


    public static class Response extends AbstractInnerMsg {
        private static final long serialVersionUID = 541137922119678855L;
        private Request request;
        private String outerOrderId;
        private boolean rs;

        public Response(Request request, String outerOrderId, boolean rs) {
            this.request = request;
            this.outerOrderId = outerOrderId;
            this.rs = rs;
        }

        public String getOuterOrderId() {
            return outerOrderId;
        }

        public Request getRequest() {
            return request;
        }

        public boolean isRs() {
            return rs;
        }
    }
}