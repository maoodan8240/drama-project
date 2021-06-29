package dm.relationship.base.msg.db.getter;

import dm.relationship.exception.PlayerPojosGetterException;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.utils.message.implement.AbstractInnerMsg;

import java.util.Map;

public class In_Game_Hashes_OnePlayerAllPojosGetter {

    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = -4907724998683401261L;
        private String playerId;
        private int outerRealmId;

        public Request(String playerId, int outerRealmId) {
            this.playerId = playerId;
            this.outerRealmId = outerRealmId;
        }

        public String getPlayerId() {
            return playerId;
        }

        public int getOuterRealmId() {
            return outerRealmId;
        }

        @Override
        public String toString() {
            return "[playerId=" + playerId + ", outerRealmId=" + outerRealmId + "]";
        }
    }


    public static class Response extends In_DbResponse {
        private static final long serialVersionUID = 2400165876339075574L;
        private Request request;
        private Map<Class<? extends TopLevelPojo>, TopLevelPojo> topLevelPojoClassToTopLevelPojo;

        public Response(Request request, Map<Class<? extends TopLevelPojo>, TopLevelPojo> topLevelPojoClassToTopLevelPojo) {
            super();
            this.request = request;
            this.topLevelPojoClassToTopLevelPojo = topLevelPojoClassToTopLevelPojo;
        }

        public Response(Request request, PlayerPojosGetterException exception) {
            super(exception);
            this.request = request;
        }

        public Request getRequest() {
            return request;
        }

        public Map<Class<? extends TopLevelPojo>, TopLevelPojo> getTopLevelPojoClassToTopLevelPojo() {
            return topLevelPojoClassToTopLevelPojo;
        }
    }

}
