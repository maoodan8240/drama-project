package dm.relationship.base.msg.db.getter;

import dm.relationship.exception.PlayerPojosGetterException;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.utils.message.implement.AbstractInnerMsg;

public class In_Game_Strings_OneIdOnePojoGetter {

    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = -7774763495024662897L;
        private int outRealmId;
        private Class<? extends TopLevelPojo> topLevelPojoClass;

        public Request(int outRealmId, Class<? extends TopLevelPojo> topLevelPojoClass) {
            this.outRealmId = outRealmId;
            this.topLevelPojoClass = topLevelPojoClass;
        }

        public int getOutRealmId() {
            return outRealmId;
        }

        public Class<? extends TopLevelPojo> getTopLevelPojoClass() {
            return topLevelPojoClass;
        }
    }


    public static class Response extends In_DbResponse {
        private static final long serialVersionUID = -3920447319666696528L;
        private Request request;
        private TopLevelPojo topLevelPojo;

        public Response(Request request, TopLevelPojo topLevelPojo) {
            super();
            this.request = request;
            this.topLevelPojo = topLevelPojo;
        }

        public Response(Request request, PlayerPojosGetterException exception) {
            super(exception);
            this.request = request;
        }

        public Request getRequest() {
            return request;
        }

        public <T extends TopLevelPojo> T getTopLevelPojo() {
            return (T) topLevelPojo;
        }
    }

}

