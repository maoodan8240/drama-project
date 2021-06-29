package dm.relationship.topLevelPojos;

import ws.common.mongoDB.interfaces.TopLevelPojo;
import dm.relationship.base.MagicWords_Redis;

import java.util.List;

public class TopLevelPojoContainer {

    public static List<Class<? extends TopLevelPojo>> getPlayerAllTopLevelClass() {
        return AllTopLevelClassHolder.getPlayerAllTopLevelClass();
    }

    public static String getGameRedisKey(Class<? extends TopLevelPojo> topLevelPojoClass, int outerRealmId) {
        return MagicWords_Redis.TopLevelPojo_Game_Prefix + outerRealmId + "_" + topLevelPojoClass.getSimpleName();
    }

    public static String getAllCommonRedisKey(Class<? extends TopLevelPojo> topLevelPojoClass) {
        return MagicWords_Redis.TopLevelPojo_All_Common_Prefix + topLevelPojoClass.getSimpleName();
    }
}
