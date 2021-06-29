package dm.relationship.base.msg.db.saver;

import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.utils.message.implement.AbstractInnerMsg;
import dm.relationship.exception.PlayerPojosGetterException;

public class In_Game_Strings_OneIdOnePojoSaver {

    public static class Request<T extends TopLevelPojo> extends AbstractInnerMsg {
        private int outRealmId;
        private T pojo;

        public Request(int outRealmId, T pojo) {
            this.outRealmId = outRealmId;
            this.pojo = pojo;
        }

        public T getPojo() {
            return pojo;
        }

        public int getOutRealmId() {
            return outRealmId;
        }
    }


    public static class Response extends AbstractInnerMsg {
        private Request request;
        private PlayerPojosGetterException exception;


        public Request getRequest() {
            return request;
        }

        public PlayerPojosGetterException getException() {
            return exception;
        }

        public Response(Request request, PlayerPojosGetterException exception) {
            this.request = request;
            this.exception = exception;
        }
    }

}

