package dm.relationship.exception;

import ws.common.mongoDB.interfaces.TopLevelPojo;

public class GetPojoFromDbException extends DmBaseException {
    private static final long serialVersionUID = 1L;

    public GetPojoFromDbException(Class<? extends TopLevelPojo> topLevelPojoClass, String id, int outerRealmId, Throwable t) {
        super(
                String.format("从数据库Redis/Mongodb获取数据异常！topLevelPojoClass=%s id=%s outerRealmId=%s ", topLevelPojoClass, id, outerRealmId),
                t);
    }
}
