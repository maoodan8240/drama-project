package dm.relationship.daos.testA;

import dm.relationship.topLevelPojos.testA.TestA;
import ws.common.mongoDB.interfaces.BaseDao;
import ws.common.mongoDB.interfaces.MongoDBClient;

public interface TestADao extends BaseDao<TestA> {


    void test();

    void test1();

    void test2();

    void initData(MongoDBClient mongoDBClient, String s);
}
