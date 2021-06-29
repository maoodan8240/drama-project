package dm.relationship.base;

import com.alibaba.fastjson.JSON;
import dm.relationship.daos.DaoContainer;
import dm.relationship.exception.GetPojoFromDbException;
import dm.relationship.topLevelPojos.TopLevelPojoContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.mongoDB.interfaces.BaseDao;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.mongoDB.utils.WsJsonUtils;
import ws.common.redis.RedisOpration;
import ws.common.redis.operation.In_RedisOperation;
import ws.common.redis.operation.RedisOprationEnum;
import ws.common.redis.operation.RedisOprationEnum.Keys;
import ws.common.redis.operation.RedisOprationEnum.Strings;
import ws.common.utils.di.GlobalInjector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PojoUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PojoUtils.class);
    private static final RedisOpration REDIS_OPRATION = GlobalInjector.getInstance(RedisOpration.class);
    private static final MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);


    //===================================================================================================
    //                            redis -- Mongodb 对应关系
    //      Game_1_Player【Redis Key (hashset类型)】                        Game_1【Mongodb库名】
    //              id1 -- value1 【索引1-内容1】             ------------>            Player 【表名】
    //              id2 -- value2 【索引2-内容2】                                          id1 -- value1 【行1--玩家1】 --- 此处为玩家Id
    //                                                                                   id2 -- value2 【行2--玩家2】
    //
    //      Game_2_Guild【Redis Key (hashset类型)】                         Game_2【Mongodb库名】
    //              id1 -- value1 【索引1-内容1】             ------------>            Guild 【表名】
    //              id2 -- value2 【索引2-内容2】                                          id1 -- value1 【行1--公会1】 --- 此处为公会Id
    //                                                                                   id2 -- value2 【行2--公会2】
    //
    //
    //      Game_1_Example【Redis Key (Strings类型)】                        Game_2【Mongodb库名】
    //            内容:Example]TopLevel的Json值               ------------>            Example 【表名】
    //                                                                                    d1 -- value1 【行1--值1】 （只一行）
    //
    //
    //      AllCommon_XXXTopLevel【Redis Key (Strings类型)】                AllCommon【Mongodb库名】
    //              Value                                  ------------>             XXXTopLevel 【表名】
    //                                                                                   id1 -- value1 【行1--值1】 （只一行）
    //
    //      AllCommon_XXXTopLevel【Redis Key (hashset类型)】               AllCommon【Mongodb库名】
    //              id1 -- value1 【索引1-内容1】            ------------>             Guild 【表名】
    //              id2 -- value2 【索引2-内容2】                                          id1 -- value1 【行1--公会1】
    //                                                                                   id2 -- value2 【行2--公会2】
    //
    //===================================================================================================


    //===================================================================================================
    //                              Mongodb
    //===================================================================================================

    /**
     * <PRE>
     * 从Mongodb中获取pojo对象
     * 形如：
     * :    Game_2【Mongodb库名】
     * :        Player 【表名】
     * :            id1 -- value1 【行1--玩家1】
     * :            id2 -- value2 【行2--玩家2】
     * </PRE>
     *
     * @param topLevelPojoClass
     * @param id
     * @param outerRealmId
     * @return
     * @throws Exception
     */
    public static <T extends TopLevelPojo> T getGamePojoFromHashesMongodb(Class<T> topLevelPojoClass, String id, int outerRealmId) throws Exception {
        return getTopLevelPojoFromMongodb(MagicWords_Redis.TopLevelPojo_Game_Prefix + outerRealmId, topLevelPojoClass, id);
    }

    /**
     * <PRE>
     * 从Mongodb中获取pojo对象
     * 形如：
     * :    Game_2【Mongodb库名】
     * :        Example 【表名】 -- 此表中只有一行
     * :            id -- value 【行1--玩家1】
     * </PRE>
     *
     * @param topLevelPojoClass
     * @param outerRealmId
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends TopLevelPojo> T getGamePojoFromStringsMongodb(Class<T> topLevelPojoClass, int outerRealmId) throws Exception {
        BaseDao<T> dao = DaoContainer.getDao(topLevelPojoClass);
        dao.init(MONGO_DB_CLIENT, MagicWords_Redis.TopLevelPojo_Game_Prefix + outerRealmId);
        List<T> rs = dao.findAll();
        return rs.size() == 0 ? null : rs.get(0);
    }

    /**
     * <PRE>
     * 从Mongodb中获取pojo对象
     * 形如：
     * :    AllCommon【Mongodb库名】
     * :        Example 【表名】 -- 此表中只有一行
     * :            id -- value 【行1--玩家1】
     * </PRE>
     *
     * @param topLevelPojoClass
     * @return
     * @throws Exception
     */
    public static <T extends TopLevelPojo> T getAllCommonPojoFromStringsMongodb(Class<T> topLevelPojoClass) throws Exception {
        BaseDao<T> dao = DaoContainer.getDao(topLevelPojoClass);
        dao.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Common);
        List<T> rs = dao.findAll();
        return rs.size() == 0 ? null : rs.get(0);
    }

    /**
     * <PRE>
     * 从Mongodb中获取pojo对象
     * 形如：
     * :    AllCommon【Mongodb库名】
     * :        Player 【表名】
     * :            id1 -- value1 【行1--玩家1】
     * :            id2 -- value2 【行2--玩家2】
     * </PRE>
     *
     * @param topLevelPojoClass
     * @param id
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends TopLevelPojo> T getAllCommonPojoFromHashesMongodb(Class<T> topLevelPojoClass, String id) throws Exception {
        return getTopLevelPojoFromMongodb(MagicWords_Mongodb.TopLevelPojo_All_Common, topLevelPojoClass, id);
    }


    /**
     * 从Mongodb,根据 库，表，id 查询
     *
     * @param dbName            库名称
     * @param topLevelPojoClass 表名称
     * @param id                表的唯一索引
     * @return
     * @throws Exception
     */
    public static <T extends TopLevelPojo> T getTopLevelPojoFromMongodb(String dbName, Class<T> topLevelPojoClass, String id) throws Exception {
        BaseDao<T> topLevelPojoDao = DaoContainer.getDao(topLevelPojoClass);
        topLevelPojoDao.init(MONGO_DB_CLIENT, dbName);
        return topLevelPojoDao.findOne(id);
    }


    /**
     * <PRE>
     * 将Pojo保存到Mongodb
     * 形如：
     * :    Game_2【Mongodb库名】
     * :        Player 【表名】
     * :            id1 -- value1 【行1--玩家1】
     * :            id2 -- value2 【行2--玩家2】
     * </PRE>
     *
     * @param pojo
     * @param outerRealmId
     */
    public static <T extends TopLevelPojo> void saveGamePojoToHashesOrStringsMongodb(T pojo, int outerRealmId) {
        LOGGER.debug("to save pojo class={} !", pojo.getClass());
        BaseDao<T> topLevelPojoDao = DaoContainer.getDao(pojo.getClass());
        topLevelPojoDao.init(MONGO_DB_CLIENT, MagicWords_Redis.TopLevelPojo_Game_Prefix + outerRealmId);
        topLevelPojoDao.insertIfExistThenReplace(pojo);
    }


    /**
     * 从Mongodb中获取pojo对象
     * 形如：
     * :    AllCommon【Mongodb库名】
     * :        Player 【表名】 （此方法支持一行或多行情况，因为根据id替换内容）
     * :            id1 -- value1 【行1--玩家1】
     * :            id2 -- value2 【行2--玩家2】
     *
     * @param pojo
     * @param <T>
     */
    public static <T extends TopLevelPojo> void saveAllCommonPojoToHashesOrStringsMongodb(T pojo) {
        BaseDao<T> topLevelPojoDao = DaoContainer.getDao(pojo.getClass());
        topLevelPojoDao.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Common);
        topLevelPojoDao.insertIfExistThenReplace(pojo);
    }

    //===================================================================================================
    //                              Redis
    //===================================================================================================


    /**
     * <PRE>
     * 从Redis中获取pojo，使用【Game_outerRealmId_XXXTopLevel】
     * Hashes结构
     * 形式： 大Key     ------>      Game_2_Arena
     * :               小Key       ---     Value
     * :               Arena1'id   ---    [Arena1]TopLevel的Json值
     * :               Arena2'id   ---    [Arena2]TopLevel的Json值
     * </PRE>
     *
     * @param topLevelPojoClass
     * @param id
     * @param outerRealmId
     * @return
     * @throws Exception
     */
    public static <T extends TopLevelPojo> T getGamePojoFromHashesRedis(Class<T> topLevelPojoClass, String id, int outerRealmId) throws Exception {
        String key = TopLevelPojoContainer.getGameRedisKey(topLevelPojoClass, outerRealmId);
        Object rsObj = REDIS_OPRATION.execute(new In_RedisOperation(RedisOprationEnum.Hashes.hget.newParmBuilder().build(key, id)));
        String targetJson = RedisOprationEnum.Hashes.hget.parseResult(rsObj);
        return JSON.parseObject(targetJson, topLevelPojoClass);
    }

    /**
     * 从Redis中获取pojo，使用【Game_outerRealmId_XXXTopLevel】
     * Strings结构
     * 形式： Key                 ---    Value
     * 形式： Game_1_Example   ---    [Example]TopLevel的Json值
     *
     * @param topLevelPojoClass
     * @param outerRealmId
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends TopLevelPojo> T getGamePojoFromStringsRedis(Class<T> topLevelPojoClass, int outerRealmId) throws Exception {
        String key = TopLevelPojoContainer.getGameRedisKey(topLevelPojoClass, outerRealmId);
        Object rsObj = REDIS_OPRATION.execute(new In_RedisOperation(Strings.get.newParmBuilder().build(key)));
        String targetJson = Strings.get.parseResult(rsObj);
        return JSON.parseObject(targetJson, topLevelPojoClass);
    }


    /**
     * 从Redis中获取pojo，使用【AllCommon_XXXTopLevel】
     * Strings结构
     * 形式： Key                 ---    Value
     * 形式： AllCommon_Example   ---    [Example]TopLevel的Json值
     *
     * @param topLevelPojoClass
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends TopLevelPojo> T getAllCommonPojoFromStringsRedis(Class<T> topLevelPojoClass) throws Exception {
        String key = TopLevelPojoContainer.getAllCommonRedisKey(topLevelPojoClass);
        Object rsObj = REDIS_OPRATION.execute(new In_RedisOperation(Strings.get.newParmBuilder().build(key)));
        String targetJson = Strings.get.parseResult(rsObj);
        return JSON.parseObject(targetJson, topLevelPojoClass);
    }


    /**
     * <PRE>
     * 从Redis中获取pojo，使用【AllCommon_XXXTopLevel】
     * Hashes结构
     * 形式： 大Key     ------>      AllCommon_Hero
     * :               小Key       ---    Value
     * :               hero1'id   ---    [Hero1]TopLevel的Json值
     * :               hero2'id   ---    [Hero2]TopLevel的Json值
     * </PRE>
     *
     * @param topLevelPojoClass
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends TopLevelPojo> T getAllCommonPojoFromHashesRedis(Class<T> topLevelPojoClass, String id) throws Exception {
        String key = TopLevelPojoContainer.getAllCommonRedisKey(topLevelPojoClass);
        Object rsObj = REDIS_OPRATION.execute(new In_RedisOperation(RedisOprationEnum.Hashes.hget.newParmBuilder().build(key, id)));
        String targetJson = RedisOprationEnum.Hashes.hget.parseResult(rsObj);
        return JSON.parseObject(targetJson, topLevelPojoClass);
    }

    /**
     * <PRE>
     * 将Pojo保存到Redis，使用【Game_outerRealmId_XXXTopLevel】
     * Hashes结构
     * 形式： 大Key     ------>      Game_2_Arena
     * :               小Key       ---     Value
     * :               Arena1'id   ---    [Arena1]TopLevel的Json值
     * :               Arena2'id   ---    [Arena2]TopLevel的Json值
     * </PRE>
     *
     * @param pojo
     * @param outerRealmId
     * @throws Exception
     */
    public static <T extends TopLevelPojo> void saveGamePojoToHashesRedis(T pojo, int outerRealmId) throws Exception {
        String key = TopLevelPojoContainer.getGameRedisKey(pojo.getClass(), outerRealmId);
        REDIS_OPRATION.execute(new In_RedisOperation(RedisOprationEnum.Hashes.hset.newParmBuilder().build(key, pojo.getOid(), WsJsonUtils.javaObjectToJSONStr(pojo))));
    }

    /**
     * <PRE>
     * 将Pojo保存到Redis，使用【Game_outerRealmId_XXXTopLevel】
     * Strings结构
     * 形式： Key                 ---    Value
     * 形式： Game_1_Example      ---    [Example]TopLevel的Json值
     * </PRE>
     *
     * @param pojo
     * @param outerRealmId
     * @param <T>
     * @throws Exception
     */
    public static <T extends TopLevelPojo> void saveGamePojoToStringsRedis(T pojo, int outerRealmId) throws Exception {
        String key = TopLevelPojoContainer.getGameRedisKey(pojo.getClass(), outerRealmId);
        REDIS_OPRATION.execute(new In_RedisOperation(Strings.set.newParmBuilder().build(key, WsJsonUtils.javaObjectToJSONStr(pojo))));
    }


    /**
     * 将Pojo保存到Redis，使用【AllCommon_XXXTopLevel】
     * Strings结构
     * 形式： Key                 ---    Value
     * 形式： AllCommon_Example   ---    [Example]TopLevel的Json值
     *
     * @param pojo
     * @param <T>
     * @throws Exception
     */
    public static <T extends TopLevelPojo> void saveAllCommonPojoToStringsRedis(T pojo) throws Exception {
        String key = TopLevelPojoContainer.getAllCommonRedisKey(pojo.getClass());
        REDIS_OPRATION.execute(new In_RedisOperation(Strings.set.newParmBuilder().build(key, WsJsonUtils.javaObjectToJSONStr(pojo))));
    }


    /**
     * <PRE>
     * 将Pojo保存到Redis，使用【AllCommon_XXXTopLevel】
     * Hashes结构
     * 形式： 大Key     ------>      AllCommon_Heros
     * :               小Key       ---    Value
     * :               hero1'id   ---    [Hero1]TopLevel的Json值
     * :               hero2'id   ---    [Hero2]TopLevel的Json值
     * </PRE>
     *
     * @param pojo
     * @param <T>
     * @throws Exception
     */
    public static <T extends TopLevelPojo> void saveAllCommonPojoToHashesRedis(T pojo) throws Exception {
        String key = TopLevelPojoContainer.getAllCommonRedisKey(pojo.getClass());
        REDIS_OPRATION.execute(new In_RedisOperation(RedisOprationEnum.Hashes.hset.newParmBuilder().build(key, pojo.getOid(), WsJsonUtils.javaObjectToJSONStr(pojo))));
    }

    /**
     * 某个Id是否在Redis中--Game--Hashes
     *
     * @param topLevelPojoClass
     * @param id
     * @param outerRealmId
     * @return
     * @throws Exception
     */
    public static boolean isIdInGameHashesRedis(Class<? extends TopLevelPojo> topLevelPojoClass, String id, int outerRealmId) throws Exception {
        String key = TopLevelPojoContainer.getGameRedisKey(topLevelPojoClass, outerRealmId); // Game_1_topLevelPojoClass
        Object rsObj = REDIS_OPRATION.execute(new In_RedisOperation(RedisOprationEnum.Hashes.hexists.newParmBuilder().build(key, id)));
        return RedisOprationEnum.Hashes.hexists.parseResult(rsObj);
    }

    /**
     * 某个Id是否在Redis中--Game--strings
     *
     * @param topLevelPojoClass
     * @param outerRealmId
     * @return
     * @throws Exception
     */
    public static boolean isIdInGameStringsRedis(Class<? extends TopLevelPojo> topLevelPojoClass, int outerRealmId) throws Exception {
        String key = TopLevelPojoContainer.getGameRedisKey(topLevelPojoClass, outerRealmId); // Game_1_topLevelPojoClass
        Object rsObj = REDIS_OPRATION.execute(new In_RedisOperation(Keys.exists.newParmBuilder().build(key)));
        return RedisOprationEnum.Keys.exists.parseResult(rsObj);
    }


    /**
     * 某个Id是否在Redis中--AllCommon--strings
     *
     * @param topLevelPojoClass
     * @return
     * @throws Exception
     */
    public static boolean isKeyInAllCommonStringsRedis(Class<? extends TopLevelPojo> topLevelPojoClass) throws Exception {
        String key = TopLevelPojoContainer.getAllCommonRedisKey(topLevelPojoClass); // AllCommon_topLevelPojoClass
        Object rsObj = REDIS_OPRATION.execute(new In_RedisOperation(RedisOprationEnum.Keys.exists.newParmBuilder().build(key)));
        return RedisOprationEnum.Hashes.hexists.parseResult(rsObj);
    }

    /**
     * 某个Id是否在Redis中--AllCommon--Hashes
     *
     * @param topLevelPojoClass
     * @param id
     * @return
     * @throws Exception
     */
    public static boolean isIdInAllCommonHashesRedis(Class<? extends TopLevelPojo> topLevelPojoClass, String id) throws Exception {
        String key = TopLevelPojoContainer.getAllCommonRedisKey(topLevelPojoClass);  // AllCommon_topLevelPojoClass
        Object rsObj = REDIS_OPRATION.execute(new In_RedisOperation(RedisOprationEnum.Hashes.hexists.newParmBuilder().build(key, id)));
        return RedisOprationEnum.Hashes.hexists.parseResult(rsObj);
    }


    //===================================================================================================
    //                              其他
    //===================================================================================================

    /**
     * 获取玩家的所有Pojo
     *
     * @param playerId
     * @param outerRealmId
     * @return
     * @throws Exception
     */
    public static Map<Class<? extends TopLevelPojo>, TopLevelPojo> getPlayerAllPojos(String playerId, int outerRealmId) throws Exception {
        Map<Class<? extends TopLevelPojo>, TopLevelPojo> topLevelPojoClassToTopLevelPojo = new HashMap<>();
        for (Class<? extends TopLevelPojo> topLevelPojoClass : TopLevelPojoContainer.getPlayerAllTopLevelClass()) {
            try {
                if (isIdInGameHashesRedis(topLevelPojoClass, playerId, outerRealmId)) {
                    TopLevelPojo pojo = getGamePojoFromHashesRedis(topLevelPojoClass, playerId, outerRealmId);
                    topLevelPojoClassToTopLevelPojo.put(topLevelPojoClass, pojo);
                } else {
                    TopLevelPojo target = getGamePojoFromHashesMongodb(topLevelPojoClass, playerId, outerRealmId);
                    if (target == null) {
                        LOGGER.debug("topLevelPojoClass={} playerId={} outerRealmId={} 尚未初始化.", topLevelPojoClass, playerId, outerRealmId);
                        continue;
                    }
                    topLevelPojoClassToTopLevelPojo.put(target.getClass(), target);
                    saveGamePojoToHashesRedis(target, outerRealmId); // 缓存进redis
                }
            } catch (Exception e) {
                throw new GetPojoFromDbException(topLevelPojoClass, playerId, outerRealmId, e);
            }
        }
        return topLevelPojoClassToTopLevelPojo;
    }


    /**
     * 获取TopLevelPojo【Game->Hashes】
     *
     * @param topLevelPojoClass
     * @param id
     * @param outerRealmId
     * @return
     * @throws Exception
     */
    public static TopLevelPojo getGamePojoFromHashes(Class<? extends TopLevelPojo> topLevelPojoClass, String id, int outerRealmId) throws Exception {
        if (isIdInGameHashesRedis(topLevelPojoClass, id, outerRealmId)) {
            return PojoUtils.getGamePojoFromHashesRedis(topLevelPojoClass, id, outerRealmId);
        } else {
            TopLevelPojo topLevelPojo = PojoUtils.getGamePojoFromHashesMongodb(topLevelPojoClass, id, outerRealmId);
            if (topLevelPojo != null) {
                saveGamePojoToHashesRedis(topLevelPojo, outerRealmId);
            }
            return topLevelPojo;
        }
    }

    /**
     * 获取TopLevelPojo-By:Redis->Game->Strings
     *
     * @param topLevelPojoClass
     * @param outerRealmId
     * @return
     * @throws Exception
     */
    public static TopLevelPojo getGamePojoFromStrings(Class<? extends TopLevelPojo> topLevelPojoClass, int outerRealmId) throws Exception {
        if (PojoUtils.isIdInGameStringsRedis(topLevelPojoClass, outerRealmId)) {
            return PojoUtils.getGamePojoFromStringsRedis(topLevelPojoClass, outerRealmId);
        } else {
            TopLevelPojo topLevelPojo = PojoUtils.getGamePojoFromStringsMongodb(topLevelPojoClass, outerRealmId);
            if (topLevelPojo != null) {
                saveGamePojoToStringsRedis(topLevelPojo, outerRealmId);
            }
            return topLevelPojo;
        }
    }


    /**
     * * 获取TopLevelPojo-By:Redis->AllCommon->Strings
     *
     * @param topLevelPojoClass
     * @return
     * @throws Exception
     */
    public static TopLevelPojo getAllCommonPojoFromStrings(Class<? extends TopLevelPojo> topLevelPojoClass) throws Exception {
        if (PojoUtils.isKeyInAllCommonStringsRedis(topLevelPojoClass)) {
            return PojoUtils.getAllCommonPojoFromStringsRedis(topLevelPojoClass);
        } else {
            TopLevelPojo topLevelPojo = PojoUtils.getAllCommonPojoFromStringsMongodb(topLevelPojoClass);
            if (topLevelPojo != null) {
                saveAllCommonPojoToStringsRedis(topLevelPojo);
            }
            return topLevelPojo;
        }
    }

    /**
     * 获取TopLevelPojo-By:Redis->AllCommon->Hashes
     *
     * @param topLevelPojoClass
     * @param id
     * @return
     * @throws Exception
     */
    public static TopLevelPojo getAllCommonPojoFromHashes(Class<? extends TopLevelPojo> topLevelPojoClass, String id) throws Exception {
        if (PojoUtils.isIdInAllCommonHashesRedis(topLevelPojoClass, id)) {
            return PojoUtils.getAllCommonPojoFromHashesRedis(topLevelPojoClass, id);
        } else {
            TopLevelPojo topLevelPojo = PojoUtils.getAllCommonPojoFromHashesMongodb(topLevelPojoClass, id);
            if (topLevelPojo != null) {
                saveAllCommonPojoToHashesRedis(topLevelPojo);
            }
            return topLevelPojo;
        }
    }
}
