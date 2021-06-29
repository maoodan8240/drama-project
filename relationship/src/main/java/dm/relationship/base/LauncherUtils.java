package dm.relationship.base;

import dm.relationship.daos.testA.TestADao;
import dm.relationship.topLevelPojos.testA.TestA;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.redis.RedisOpration;
import ws.common.redis.jedis.interfaces.JedisClient;
import ws.common.redis.operation.In_RedisOperation;
import ws.common.redis.operation.RedisOprationEnum;
import ws.common.utils.di.GlobalInjector;

public class LauncherUtils {
    private static final Logger logger = LoggerFactory.getLogger(LauncherUtils.class);

    public static void _redisInit_Test() {
        RedisOpration redisOpration = GlobalInjector.getInstance(RedisOpration.class);
        redisOpration.execute(new In_RedisOperation(RedisOprationEnum.Keys.del.newParmBuilder().build("LauncherTest")));
        Object rsObj2 = redisOpration.execute(new In_RedisOperation(RedisOprationEnum.Strings.set.newParmBuilder().build("LauncherTest", "Test")));
        logger.debug("Redis测试：" + RedisOprationEnum.Strings.get.parseResult(rsObj2));
    }

    /**
     * 需要加载ExampleDao
     */
    public static void _mongodbInit_Test() throws Exception {
        MongoDBClient mongoDBClient = GlobalInjector.getInstance(MongoDBClient.class);
        TestADao testADao = GlobalInjector.getInstance(TestADao.class);
        String playerId = ObjectId.get().toString();
        TestA target = new TestA(playerId);
        testADao.initData(mongoDBClient, MagicWords_Mongodb.GameDataBaseNamePrefix + 1);
        testADao.insertIfExistThenReplace(target);
        logger.debug("mongodb测试：存储Test playerId=" + playerId);
        testADao.initData(mongoDBClient, MagicWords_Mongodb.GameDataBaseNamePrefix + 1);
        TopLevelPojo mails = testADao.findOne(playerId);
        logger.debug("mongodb测试：读取Test playerId=" + mails.getOid());
    }

    public static void _redisInit_SingleTest() {
        JedisClient jedisClient = GlobalInjector.getInstance(JedisClient.class);
        Jedis jedis = jedisClient.getJedis();
        jedis.select(0);
        jedis.del("LauncherTest");
        jedis.set("LauncherTest", "test");
        jedisClient.returnResource(jedis);
        logger.debug("Redis TEST:" + "LauncherTest ,test");
    }
}
