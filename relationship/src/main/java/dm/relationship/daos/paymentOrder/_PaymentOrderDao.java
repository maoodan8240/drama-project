package dm.relationship.daos.paymentOrder;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import ws.common.mongoDB.implement.AbstractBaseDao;
import ws.common.utils.date.WsDateFormatEnum;
import ws.common.utils.date.WsDateUtils;
import dm.relationship.daos.utils.DaoUtils;
import dm.relationship.topLevelPojos.paymentOrder.PaymentOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class _PaymentOrderDao extends AbstractBaseDao<PaymentOrder> implements PaymentOrderDao {

    public _PaymentOrderDao() {
        super(PaymentOrder.class);
    }

    @Override
    public PaymentOrder findOneByInnerOrderId(String innerOrderId) {
        Document document = new Document("innerOrderId", innerOrderId);
        return findOne(document);
    }


    @Override
    public int findNewPlayerNewPaymentCount() {
        String data = WsDateUtils.dateToFormatStr(new Date(), WsDateFormatEnum.yyyyMMdd);
        Document document = new Document();
        document.append("pyCreateAtDate", Integer.valueOf(data));
        document.append("endDate", Integer.valueOf(data));
        document.append("orderStatus", "OD_SUCCESS");
        return DaoUtils.findDistinctFieldAndCondition(this.getMongoCollection(), "playerId", document);
    }

    @Override
    public int findPaymentCount() {
        String data = WsDateUtils.dateToFormatStr(new Date(), WsDateFormatEnum.yyyyMMdd);
        Document document = new Document();
        document.append("endDate", Integer.valueOf(data));
        document.append("orderStatus", "OD_SUCCESS");
        return DaoUtils.findDistinctFieldAndCondition(this.getMongoCollection(), "playerId", document);
    }

    @Override
    public int findPaymentSum() {
        List<BasicDBObject> listy = new ArrayList<>();
        String data = WsDateUtils.dateToFormatStr(new Date(), WsDateFormatEnum.yyyyMMdd);
        BasicDBObject match = new BasicDBObject("$match", new BasicDBObject("endDate", Integer.valueOf(data)));
        BasicDBObject group = new BasicDBObject("_id", "$endDate");
        BasicDBObject count = new BasicDBObject("$sum", "$rmb");
        BasicDBObject obj = new BasicDBObject();
        obj.put("$group", group.append("count", count));
        listy.add(match);
        listy.add(obj);
        System.out.println(JSON.toJSONString(listy));
        AggregateIterable<Document> collection = getMongoCollection().aggregate(listy).allowDiskUse(true);
        MongoCursor<Document> xx = collection.iterator();
        if (xx.hasNext()) {
            Document doc = xx.next();
            return Integer.valueOf(doc.get("count").toString());
        }
        return 0;

    }

    @Override
    public List<PaymentOrder> findRechargeRecordByCondition(String endDate, String platformType, int orid) {
        Document document = new Document();
        document.append("endDate", Integer.valueOf(endDate));
        if (!StringUtils.isBlank(platformType) && !platformType.equals("0")) {
            document.append("platformType", platformType);
        }
        if (orid > 0) {
            document.append("outerRealmId", orid);
        }
        return findAll(document);
    }

    @Override
    public List<PaymentOrder> findReChargeRecordBySimpleId(int simpleId) {
        Document document = new Document();
        document.append("simpleId", simpleId);
        return findAll(document);
    }

    @Override
    public int findNewPlayerNewPaymentCountByDate(String date, String platformType, int orid) {
        Document document = new Document();
        document.append("pyCreateAtDate", Integer.valueOf(date));
        document.append("endDate", Integer.valueOf(date));
        document.append("orderStatus", "OD_SUCCESS");
        if (!StringUtils.isBlank(platformType) && !platformType.equals("0")) {
            document.append("platformType", platformType);
        }
        if (orid > 0) {
            document.append("outerRealmId", orid);
        }
        return DaoUtils.findDistinctFieldAndCondition(this.getMongoCollection(), "playerId", document);
    }

    @Override
    public int findPaymentCountByDate(String date, String platformType, int orid) {
        Document document = new Document();
        document.append("endDate", Integer.valueOf(date));
        document.append("orderStatus", "OD_SUCCESS");
        if (!StringUtils.isBlank(platformType) && !platformType.equals("0")) {
            document.append("platformType", platformType);
        }
        if (orid > 0) {
            document.append("outerRealmId", orid);
        }
        return DaoUtils.findDistinctFieldAndCondition(this.getMongoCollection(), "playerId", document);
    }

    @Override
    public int findPaymentSumByDate(String date, String platformType, int orid) {
        List<BasicDBObject> listy = new ArrayList<>();
        BasicDBObject mathCondition = new BasicDBObject("endDate", Integer.valueOf(date));
        if (!StringUtils.isBlank(platformType) && !platformType.equals("0")) {
            mathCondition.append("platformType", platformType);
        }
        if (orid != 0) {
            mathCondition.append("orid", orid);
        }
        BasicDBObject match = new BasicDBObject();
        match.put("$match", mathCondition);
        BasicDBObject group = new BasicDBObject("_id", "$endDate");
        BasicDBObject count = new BasicDBObject("$sum", "$rmb");
        BasicDBObject obj = new BasicDBObject();
        obj.put("$group", group.append("count", count));
        listy.add(match);
        listy.add(obj);
        System.out.println(JSON.toJSONString(listy));
        AggregateIterable<Document> collection = getMongoCollection().aggregate(listy).allowDiskUse(true);
        MongoCursor<Document> xx = collection.iterator();
        if (xx.hasNext()) {
            Document doc = xx.next();
            return Integer.valueOf(doc.get("count").toString());
        }
        return 0;

    }

}
