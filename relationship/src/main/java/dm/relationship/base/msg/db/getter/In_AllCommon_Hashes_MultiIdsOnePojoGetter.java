package dm.relationship.base.msg.db.getter;

import dm.relationship.exception.PlayerPojosGetterException;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.utils.message.implement.AbstractInnerMsg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class In_AllCommon_Hashes_MultiIdsOnePojoGetter {

    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = -5368181391373684923L;
        private List<String> ids = new ArrayList<>();
        private Class<? extends TopLevelPojo> topLevelPojoClass;

        public Request(List<String> ids, Class<? extends TopLevelPojo> topLevelPojoClass) {
            this.ids.clear();
            this.ids.addAll(ids);
            this.topLevelPojoClass = topLevelPojoClass;
        }

        public List<String> getIds() {
            return ids;
        }

        public Class<? extends TopLevelPojo> getTopLevelPojoClass() {
            return topLevelPojoClass;
        }
    }


    public static class Response extends In_DbResponse {
        private static final long serialVersionUID = -5713069671732339943L;
        private Request request;
        private Map<String, TopLevelPojo> idTotopLevelPojos;


        public Request getRequest() {
            return request;
        }

        public Map<String, TopLevelPojo> getIdTotopLevelPojos() {
            return idTotopLevelPojos;
        }

        public Response(Request request, Map<String, TopLevelPojo> playerIdsTotopLevelPojos) {
            super();
            this.request = request;
            this.idTotopLevelPojos = playerIdsTotopLevelPojos;
        }

        public Response(Request request, PlayerPojosGetterException exception) {
            super(exception);
            this.request = request;
        }
    }
}
