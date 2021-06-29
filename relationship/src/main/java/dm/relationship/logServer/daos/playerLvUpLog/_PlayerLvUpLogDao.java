package dm.relationship.logServer.daos.playerLvUpLog;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import ws.common.mongoDB.implement.AbstractBaseDao;
import dm.relationship.logServer.pojos.PlayerLvUpLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zww on 8/10/16.
 */
public class _PlayerLvUpLogDao extends AbstractBaseDao<PlayerLvUpLog> implements PlayerLvUpLogDao {
    public _PlayerLvUpLogDao() {
        super(PlayerLvUpLog.class);
    }

    @Override
    public List<Integer> findLvByDate(String createAtTime, String endDate, String platformType, int orid) {
        List<Integer> lvs = new ArrayList<>();
        List<BasicDBObject> listy = new ArrayList<>();

        BasicDBObject mathCondition = new BasicDBObject("createAtDate", Integer.valueOf(createAtTime));
        mathCondition.append("date", new BasicDBObject("$lte", Integer.valueOf(endDate)));
        if (!StringUtils.isBlank(platformType) && !platformType.equals("0")) {
            mathCondition.append("platformType", platformType);
        }
        if (orid != 0) {
            mathCondition.append("orid", orid);
        }
        BasicDBObject match = new BasicDBObject();
        match.put("$match", mathCondition);
        BasicDBObject group = new BasicDBObject("_id", "$pid");
        BasicDBObject count = new BasicDBObject("$max", "$lv");
        BasicDBObject obj = new BasicDBObject();
        obj.put("$group", group.append("max_value", count));
        listy.add(match);
        listy.add(obj);
        System.out.println(JSON.toJSONString(listy));
        AggregateIterable<Document> collection = getMongoCollection().aggregate(listy).allowDiskUse(true);
        MongoCursor<Document> xx = collection.iterator();
        while (xx.hasNext()) {
            Document doc = xx.next();
            lvs.add(Integer.valueOf(doc.get("max_value").toString()));
        }
        return lvs;
    }
}
