package dm.relationship.base.msg.db.getter;

import dm.relationship.exception.PlayerPojosGetterException;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.utils.message.implement.AbstractInnerMsg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class In_Game_Hashes_MultiIdsOnePojoGetter {

    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = 6353346284683791226L;

        private List<String> ids = new ArrayList<>();
        private int outRealmId;
        private Class<? extends TopLevelPojo> topLevelPojoClass;

        public Request(List<String> ids, int outRealmId, Class<? extends TopLevelPojo> topLevelPojoClass) {
            this.ids.clear();
            this.ids.addAll(ids);
            this.outRealmId = outRealmId;
            this.topLevelPojoClass = topLevelPojoClass;
        }

        public List<String> getIds() {
            return ids;
        }

        public int getOutRealmId() {
            return outRealmId;
        }

        public Class<? extends TopLevelPojo> getTopLevelPojoClass() {
            return topLevelPojoClass;
        }
    }


    public static class Response extends In_DbResponse {
        private static final long serialVersionUID = 1038942496943945599L;

        private Request request;
        private Map<String, TopLevelPojo> idTotopLevelPojos;


        public Request getRequest() {
            return request;
        }

        public Map<String, TopLevelPojo> getIdTotopLevelPojos() {
            return idTotopLevelPojos;
        }

        @SuppressWarnings("unchecked")
        public <T extends TopLevelPojo> Map<String, T> getSpecificIdTotopLevelPojos() {
            Map<String, T> ret = new HashMap<>();
            for (Entry<String, TopLevelPojo> entry : idTotopLevelPojos.entrySet()) {
                ret.put(entry.getKey(), (T) entry.getValue());
            }
            return ret;
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
