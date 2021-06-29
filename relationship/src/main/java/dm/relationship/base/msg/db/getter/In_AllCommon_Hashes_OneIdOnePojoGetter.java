package dm.relationship.base.msg.db.getter;

import dm.relationship.exception.PlayerPojosGetterException;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.utils.message.implement.AbstractInnerMsg;

public class In_AllCommon_Hashes_OneIdOnePojoGetter {

    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = 6108807835072058284L;
        private String id;
        private Class<? extends TopLevelPojo> topLevelPojoClass;


        public Request(String playerId, Class<? extends TopLevelPojo> topLevelPojoClass) {
            this.id = playerId;
            this.topLevelPojoClass = topLevelPojoClass;
        }

        public String getId() {
            return id;
        }

        public Class<? extends TopLevelPojo> getTopLevelPojoClass() {
            return topLevelPojoClass;
        }
    }


    public static class Response extends In_DbResponse {
        private static final long serialVersionUID = -5200084383240402821L;
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

