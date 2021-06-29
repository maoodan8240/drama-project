package dm.relationship.base.msg;

import ws.common.utils.message.implement.AbstractInnerMsg;

public class In_BroadcastEachMinute {

    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = 6033999077677998549L;
        private int hour;
        private int minute;

        public Request(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }

        public int getHour() {
            return hour;
        }

        public int getMinute() {
            return minute;
        }
    }


    public static class Response extends AbstractInnerMsg {
        private static final long serialVersionUID = 8103237583328395467L;
        private Request request;

        public Request getRequest() {
            return request;
        }

    }
}
