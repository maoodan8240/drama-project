
package dm.relationship.base.msg.db.saver;

import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.utils.message.implement.AbstractInnerMsg;
import dm.relationship.exception.PlayerPojosGetterException;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求存储相同outRealmId下的多个TopLevelPojo
 */
public class In_Game_Hashes_OneId_MultiPojos_SameOutRealmId_Saver {

    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = -4420171800827642528L;
        private int outRealmId;
        private List<TopLevelPojo> pojoLis = new ArrayList<>();

        public Request(int outRealmId, TopLevelPojo pojo) {
            this.outRealmId = outRealmId;
            this.pojoLis.add(pojo);
        }

        public Request(int outRealmId, List<TopLevelPojo> pojoLis) {
            this.outRealmId = outRealmId;
            this.pojoLis.addAll(pojoLis);
        }

        public int getOutRealmId() {
            return outRealmId;
        }

        public List<TopLevelPojo> getPojoLis() {
            return pojoLis;
        }
    }


    public static class Response extends AbstractInnerMsg {
        private Request request;
        private PlayerPojosGetterException exception;

        public Response(Request request, PlayerPojosGetterException exception) {
            this.request = request;
            this.exception = exception;
        }
    }
}

