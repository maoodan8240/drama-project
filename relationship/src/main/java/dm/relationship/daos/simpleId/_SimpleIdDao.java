package dm.relationship.daos.simpleId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.bson.Document;
import ws.common.mongoDB.handler.PojoHandler;
import ws.common.mongoDB.implement.AbstractBaseDao;
import ws.common.mongoDB.utils.UpdateOperators;
import dm.relationship.base.MagicNumbers;
import dm.relationship.enums.SimpleIdTypeEnum;
import dm.relationship.topLevelPojos.simpleId.SimpleId;

public class _SimpleIdDao extends AbstractBaseDao<SimpleId> implements SimpleIdDao {

    public _SimpleIdDao() {
        super(SimpleId.class);
    }

    @Override
    public int nextSimpleId(int outerRealmId, SimpleIdTypeEnum type) {
        BasicDBObject find = new BasicDBObject("outerRealmId", outerRealmId);
        find.put("type", type.toString());

        BasicDBObject update = new BasicDBObject();
        update.put(UpdateOperators.INC.getOperators(), new BasicDBObject("simpleId", 1));

        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.upsert(true);
        Document document = getMongoCollection().findOneAndUpdate(find, update, options);
        int base = MagicNumbers.MIN_SIMPLEID * outerRealmId;
        if (document == null) {
            return base;
        }
        SimpleId simpleId = PojoHandler.documentToPojo(document, cls);
        return base + simpleId.getSimpleId();
    }

    @Override
    public int nextSimpleId(SimpleIdTypeEnum type) {
        return nextSimpleId(MagicNumbers.DEFAULT_ONE, type);
    }
}