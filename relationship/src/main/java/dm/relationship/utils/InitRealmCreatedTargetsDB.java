package dm.relationship.utils;

import org.bson.types.ObjectId;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.utils.di.GlobalInjector;
import dm.relationship.base.MagicWords_Redis;
import dm.relationship.daos.realmCreatedTargets.RealmCreatedTargetsDao;
import dm.relationship.topLevelPojos.common.RealmCreatedTargets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lee on 17-3-28.
 */
public class InitRealmCreatedTargetsDB {
    private static final MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);
    // RealmCreatedTargets不缓存Redis
    private static Map<Integer, RealmCreatedTargetsDao> outerRealmIdToDao = new HashMap<>();
    private static Map<Integer, Boolean> outerRealmIdToIsInit = new HashMap<>();

    /**
     * 初始化，没有RealmCreatedTargets则创建，并检查
     */
    private static void init(int outerRealmId) {
        if (outerRealmIdToIsInit.containsKey(outerRealmId) && outerRealmIdToIsInit.get(outerRealmId)) {
            return;
        }
        List<RealmCreatedTargets> targetsLis = getTargetsDao(outerRealmId).findAll();
        if (targetsLis.size() == 0) {
            RealmCreatedTargets createdTargets = new RealmCreatedTargets(ObjectId.get().toString());
            createdTargets.setOuterRealmId(outerRealmId);
            save(outerRealmId, createdTargets);
            outerRealmIdToIsInit.put(outerRealmId, true);
        }
    }


    /**
     * 保存
     *
     * @param createdTargets
     */
    private static void save(int outerRealmId, RealmCreatedTargets createdTargets) {
        getTargetsDao(outerRealmId).insert(createdTargets);
    }


    /**
     * 添加初始化了的Target
     *
     * @param topLevelPojo
     */
    public static boolean update(int outerRealmId, TopLevelPojo topLevelPojo) {
        init(outerRealmId);
        return getTargetsDao(outerRealmId).addTopLevelPojo(topLevelPojo);
    }

    /**
     * 添加初始化了的Target
     *
     * @param topLevelPojoSimpleName
     */
    public static boolean update(int outerRealmId, String topLevelPojoSimpleName) {
        init(outerRealmId);
        return getTargetsDao(outerRealmId).addTopLevelPojo(topLevelPojoSimpleName);
    }

    /**
     * topLevelPojo是否已经初始化
     *
     * @param topLevelPojo
     * @return
     */
    public static boolean containsTopLevelPojo(int outerRealmId, TopLevelPojo topLevelPojo) {
        init(outerRealmId);
        return getTargetsDao(outerRealmId).containsTopLevelPojo(topLevelPojo);
    }

    /**
     * topLevelPojo是否已经初始化
     *
     * @param topLevelPojoSimpleName
     * @return
     */
    public static boolean containsTopLevelPojo(int outerRealmId, String topLevelPojoSimpleName) {
        init(outerRealmId);
        return getTargetsDao(outerRealmId).containsTopLevelPojo(topLevelPojoSimpleName);
    }


    /**
     * 获取RealmCreatedTargetsDao
     *
     * @param outerRealmId
     * @return
     */
    public static RealmCreatedTargetsDao getTargetsDao(int outerRealmId) {
        if (outerRealmIdToDao.containsKey(outerRealmId)) {
            return outerRealmIdToDao.get(outerRealmId);
        }
        RealmCreatedTargetsDao targetsDao = GlobalInjector.getInstance(RealmCreatedTargetsDao.class);
        targetsDao.init(MONGO_DB_CLIENT, MagicWords_Redis.TopLevelPojo_Game_Prefix + outerRealmId);
        outerRealmIdToDao.put(outerRealmId, targetsDao);
        return targetsDao;
    }
}
