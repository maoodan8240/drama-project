package dm.relationship.daos.testA;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.UpdateOptions;
import dm.relationship.topLevelPojos.testA.TestA;
import org.bson.Document;
import ws.common.mongoDB.implement.AbstractBaseDao;
import ws.common.mongoDB.interfaces.MongoDBClient;

import java.util.HashMap;
import java.util.Map;

public class _TestADao extends AbstractBaseDao<TestA> implements TestADao {

    public _TestADao() {
        super(TestA.class);
    }


    @Override
    public void test() {
        Document document = new Document("name", 4);
        getMongoCollection().insertOne(document);
    }


    @Override
    public void test1() {
        if (1 == 2) { // 不存在则插入
            // 错误方式
            // Exception in thread "main" java.lang.IllegalArgumentException: Invalid BSON field name age
            Document d1 = new Document("name", 2);
            Document d2 = new Document("age", 4);
            getMongoCollection().updateOne(d1, d2);
        }

        if (1 == 2) { // 不存在则插入
            Document d1 = new Document("name", 2);
            Document d2 = new Document("age", 1);
            Document d3 = new Document("$set", d2);
            UpdateOptions options = new UpdateOptions();
            options.upsert(true);
            getMongoCollection().updateOne(d1, d3, options);
        }
        if (1 == 2) {  // 更新值
            Document d1 = new Document("name", 2);
            Document d2 = new Document("age", 3);
            Document d3 = new Document("$set", d2);
            getMongoCollection().updateOne(d1, d3);
        }

        if (1 == 2) {
            Map<Integer, String> map = new HashMap<>();
            map.put(1, "11111111");
            map.put(2, "22222222");
            map.put(3, "33333333");

            Document d1 = new Document("name", 2);
            Document d2 = new Document("map", JSON.toJSON(map));
            Document d3 = new Document("$set", d2);
            UpdateOptions options = new UpdateOptions();
            options.upsert(true);
            getMongoCollection().updateOne(d1, d3, options);
        }
        if (1 == 2) { // map 添加5key值
            Document d1 = new Document("name", 2);
            Document d2 = new Document("map.5", "444");
            Document d3 = new Document("$set", d2);
            UpdateOptions options = new UpdateOptions();
            options.upsert(true);
            getMongoCollection().updateOne(d1, d3, options);
        }
        if (1 == 2) { // 移除 3key值
            Document d1 = new Document("name", 2);
            Document d2 = new Document("map.3", "11111");
            Document d3 = new Document("$unset", d2);
            UpdateOptions options = new UpdateOptions();
            options.upsert(true);
            getMongoCollection().updateOne(d1, d3, options);
        }
        if (1 == 1) { // 移除 3key值
            Document d1 = new Document("name", 2);
            Document d2 = new Document("set1", "{'a','b'}");
            Document d3 = new Document("$push", d2);
            UpdateOptions options = new UpdateOptions();
            options.upsert(true);
            getMongoCollection().updateOne(d1, d3, options);
        }
    }

    @Override
    public void test2() {
        Document d2 = new Document();
        Document d1 = new Document();
        d1.append("name", 2);
        d1.append("map.1", "11111111");
        d2.append("map.1", 1);
        d2.append("_id", 0);
        FindIterable<Document> iterable = getMongoCollection().find(d1).projection(d2);
        Document document = iterable.first();
        System.out.println(document);
    }

    @Override
    public void initData(MongoDBClient mongoDBClient, String s) {
        this.mongoDbClient = mongoDBClient;
        this.mongoDatabase = mongoDBClient.getSpecifiedDatabase(s);
        this.mongoCollection = this.mongoDatabase.getCollection(this.cls.getSimpleName());
    }
}
