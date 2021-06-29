package dm.relationship.base.msg;

import ws.common.utils.message.implement.AbstractInnerMsg;

public class In_BroadcastEachHour {

    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = -1053814797274288365L;
        private int hour;

        public Request(int hour) {
            this.hour = hour;
        }

        public int getHour() {
            return hour;
        }
    }


    public static class Response extends AbstractInnerMsg {
        private static final long serialVersionUID = -4968363827360374478L;
        private Request request;

        public Request getRequest() {
            return request;
        }

    }
}
