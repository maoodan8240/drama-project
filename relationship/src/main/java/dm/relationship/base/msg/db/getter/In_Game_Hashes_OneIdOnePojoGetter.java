package dm.relationship.base.msg.db.getter;

import dm.relationship.exception.PlayerPojosGetterException;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.utils.message.implement.AbstractInnerMsg;

public class In_Game_Hashes_OneIdOnePojoGetter {

    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = 7733466294223150596L;

        private String id;
        private int outerRealmId;
        private Class<? extends TopLevelPojo> topLevelPojoClass;

        public Request(String id, int outerRealmId, Class<? extends TopLevelPojo> topLevelPojoClass) {
            this.id = id;
            this.outerRealmId = outerRealmId;
            this.topLevelPojoClass = topLevelPojoClass;
        }

        public String getId() {
            return id;
        }

        public int getOuterRealmId() {
            return outerRealmId;
        }

        public Class<? extends TopLevelPojo> getTopLevelPojoClass() {
            return topLevelPojoClass;
        }
    }


    public static class Response extends In_DbResponse {
        private static final long serialVersionUID = -2923436166827277413L;

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

        public TopLevelPojo getTopLevelPojo() {
            return topLevelPojo;
        }
    }

}

