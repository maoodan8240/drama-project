package dm.relationship.utils;

import ws.common.mongoDB.interfaces.TopLevelPojo;
import dm.relationship.base.GlobalClusterContext;
import dm.relationship.base.ServerRoleEnum;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.db.getter.In_Game_Hashes_MultiIdsOnePojoGetter;
import dm.relationship.base.msg.db.getter.In_Game_Hashes_OneIdOnePojoGetter;
import dm.relationship.base.msg.db.getter.In_Game_Strings_OneIdOnePojoGetter;
import dm.relationship.base.msg.db.saver.In_Game_Hashes_OneId_MultiPojos_SameOutRealmId_Saver;
import dm.relationship.base.msg.db.saver.In_Game_Strings_OneIdOnePojoSaver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lee on 17-4-5.
 */
public class DBUtils {
    @SuppressWarnings("unchecked")
    public static <T extends TopLevelPojo> T getStringPojo(int outerRealmId, Class<T> topLevelPojoClass) {
        In_Game_Strings_OneIdOnePojoGetter.Request request = new In_Game_Strings_OneIdOnePojoGetter.Request(outerRealmId, topLevelPojoClass);
        In_Game_Strings_OneIdOnePojoGetter.Response response = ActorMsgSynchronizedExecutor.sendMsgToSingleServerIgnoreEnv(ServerRoleEnum.DM_MongodbRedisServer, GlobalClusterContext.getContext(), ActorSystemPath.DM_MongodbRedisServer_Selection_PojoGetterManager, request);
        RelationshipCommonUtils.checkDbResponse(response);
        TopLevelPojo topLevelPojo = response.getTopLevelPojo();
        return (T) topLevelPojo;
    }

    public static void saveStringPojo(int outerRealmId, TopLevelPojo topLevelPojo) {
        In_Game_Strings_OneIdOnePojoSaver.Request<TopLevelPojo> request = new In_Game_Strings_OneIdOnePojoSaver.Request<>(outerRealmId, topLevelPojo);
        In_Game_Strings_OneIdOnePojoSaver.Response response = ActorMsgSynchronizedExecutor.sendMsgToSingleServerIgnoreEnv(ServerRoleEnum.DM_MongodbRedisServer, GlobalClusterContext.getContext(), ActorSystemPath.DM_MongodbRedisServer_Selection_PojoSaver, request);
        // todo 是否检查response
    }

    public static void saveHashPojo(int outerRealmId, List<TopLevelPojo> needSavePojos) {
        In_Game_Hashes_OneId_MultiPojos_SameOutRealmId_Saver.Request request = new In_Game_Hashes_OneId_MultiPojos_SameOutRealmId_Saver.Request(outerRealmId, needSavePojos);
        ClusterMessageSender.sendMsgToServer(GlobalClusterContext.getContext(), ServerRoleEnum.DM_MongodbRedisServer, request, ActorSystemPath.DM_MongodbRedisServer_Selection_PojoSaver);
    }

    public static void saveHashPojo(int outerRealmId, TopLevelPojo needSavePojo) {
        List<TopLevelPojo> needSavePojos = new ArrayList<>();
        needSavePojos.add(needSavePojo);
        saveHashPojo(outerRealmId, needSavePojos);
    }


    @SuppressWarnings("unchecked")
    public static <T extends TopLevelPojo> T getHashPojo(String id, int outRealmId, Class<T> topLevelPojoClass) {
        In_Game_Hashes_OneIdOnePojoGetter.Request request = new In_Game_Hashes_OneIdOnePojoGetter.Request(id, outRealmId, topLevelPojoClass);
        In_Game_Hashes_OneIdOnePojoGetter.Response response = ActorMsgSynchronizedExecutor.sendMsgToSingleServerIgnoreEnv(ServerRoleEnum.DM_MongodbRedisServer, GlobalClusterContext.getContext(), ActorSystemPath.DM_MongodbRedisServer_Selection_PojoGetterManager, request);
        RelationshipCommonUtils.checkDbResponse(response);
        TopLevelPojo topLevelPojo = response.getTopLevelPojo();
        return (T) topLevelPojo;
    }


    /**
     * 获取多个id的同一类型的POJO
     *
     * @param ids
     * @param outerRealmId
     * @param topLevelPojoClass
     * @param <T>
     * @return
     */
    public static <T extends TopLevelPojo> Map<String, T> getHashPojoLis(List<String> ids, int outerRealmId, Class<T> topLevelPojoClass) {
        In_Game_Hashes_MultiIdsOnePojoGetter.Request request = new In_Game_Hashes_MultiIdsOnePojoGetter.Request(ids, outerRealmId, topLevelPojoClass);
        In_Game_Hashes_MultiIdsOnePojoGetter.Response response = ActorMsgSynchronizedExecutor.sendMsgToSingleServerIgnoreEnv(ServerRoleEnum.DM_MongodbRedisServer, GlobalClusterContext.getContext(), ActorSystemPath.DM_MongodbRedisServer_Selection_PojoGetterManager, request);
        RelationshipCommonUtils.checkDbResponse(response);
        Map<String, T> pojoLis = response.getSpecificIdTotopLevelPojos();
        return pojoLis;
    }
}
