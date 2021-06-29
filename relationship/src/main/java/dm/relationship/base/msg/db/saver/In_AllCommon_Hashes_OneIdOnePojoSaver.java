package dm.relationship.base.msg.db.saver;

import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.utils.message.implement.AbstractInnerMsg;
import dm.relationship.exception.PlayerPojosGetterException;

public class In_AllCommon_Hashes_OneIdOnePojoSaver {

    public static class Request<T extends TopLevelPojo> extends AbstractInnerMsg {
        private T pojo;

        public Request(T pojo) {
            this.pojo = pojo;
        }

        public T getPojo() {
            return pojo;
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

