package dm.relationship.base.msg.db.getter;

import dm.relationship.exception.PlayerPojosGetterException;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.utils.message.implement.AbstractInnerMsg;

public class In_AllCommon_Strings_OneIdOnePojoGetter {

    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = 2339166269005594604L;

        private Class<? extends TopLevelPojo> topLevelPojoClass;


        public Request(Class<? extends TopLevelPojo> topLevelPojoClass) {
            this.topLevelPojoClass = topLevelPojoClass;
        }

        public Class<? extends TopLevelPojo> getTopLevelPojoClass() {
            return topLevelPojoClass;
        }
    }


    public static class Response extends In_DbResponse {
        private static final long serialVersionUID = -7480673703934739419L;

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

