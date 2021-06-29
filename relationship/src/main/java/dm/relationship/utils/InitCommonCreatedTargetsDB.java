package dm.relationship.utils;

import org.bson.types.ObjectId;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.utils.di.GlobalInjector;
import dm.relationship.base.MagicWords_Mongodb;
import dm.relationship.daos.commonCreatedTargets.CommonCreatedTargetsDao;
import dm.relationship.topLevelPojos.common.CommonCreatedTargets;

import java.util.List;

/**
 * Created by lee on 17-3-28.
 */
public class InitCommonCreatedTargetsDB {
    private static final MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);
    // CommonCreatedTargets不缓存Redis
    private static final CommonCreatedTargetsDao TARGETS_DAO = GlobalInjector.getInstance(CommonCreatedTargetsDao.class);

    static {
        TARGETS_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Common);
    }

    /**
     * 初始化，没有CommonCreatedTargets则创建，并检查
     */
    public static void init() {
        List<CommonCreatedTargets> targetsLis = TARGETS_DAO.findAll();
        if (targetsLis.size() == 0) {
            CommonCreatedTargets createdTargets = new CommonCreatedTargets(ObjectId.get().toString());
            save(createdTargets);
        }
    }


    /**
     * 保存
     *
     * @param createdTargets
     */
    private static void save(CommonCreatedTargets createdTargets) {
        TARGETS_DAO.insert(createdTargets);
    }


    /**
     * 添加初始化了的Target
     *
     * @param topLevelPojo
     */
    public static boolean update(TopLevelPojo topLevelPojo) {
        return TARGETS_DAO.addTopLevelPojo(topLevelPojo);
    }

    /**
     * 添加初始化了的Target
     *
     * @param topLevelPojoSimpleName
     */
    public static boolean update(String topLevelPojoSimpleName) {
        return TARGETS_DAO.addTopLevelPojo(topLevelPojoSimpleName);
    }

    /**
     * topLevelPojo是否已经初始化
     *
     * @param topLevelPojo
     * @return
     */
    public static boolean containsTopLevelPojo(TopLevelPojo topLevelPojo) {
        return TARGETS_DAO.containsTopLevelPojo(topLevelPojo);
    }

    /**
     * topLevelPojo是否已经初始化
     *
     * @param topLevelPojoSimpleName
     * @return
     */
    public static boolean containsTopLevelPojo(String topLevelPojoSimpleName) {
        return TARGETS_DAO.containsTopLevelPojo(topLevelPojoSimpleName);
    }
}
