package dm.relationship.daos.commonCreatedTargets;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.bson.Document;
import org.bson.types.ObjectId;
import ws.common.mongoDB.implement.AbstractBaseDao;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.mongoDB.utils.UpdateOperators;
import dm.relationship.topLevelPojos.common.CommonCreatedTargets;

public class _CommonCreatedTargetsDao extends AbstractBaseDao<CommonCreatedTargets> implements CommonCreatedTargetsDao {

    public _CommonCreatedTargetsDao() {
        super(CommonCreatedTargets.class);
    }


    @Override
    public boolean containsTopLevelPojo(TopLevelPojo topLevelPojo) {
        return containsTopLevelPojo(topLevelPojo.getClass().getSimpleName());
    }


    @Override
    public boolean containsTopLevelPojo(String topLevelPojoSimpleName) {
        Document document = new Document("targetNames", topLevelPojoSimpleName);
        CommonCreatedTargets createdTargets = findOne(document);
        return createdTargets != null;
    }

    @Override
    public boolean addTopLevelPojo(TopLevelPojo topLevelPojo) {
        String simpleName = topLevelPojo.getClass().getSimpleName();
        return addTopLevelPojo(simpleName);
    }

    @Override
    public boolean addTopLevelPojo(String topLevelPojoSimpleName) {
        CommonCreatedTargets createdTargets = findTheOnlyOne();
        Document find = new Document("_id", new ObjectId(createdTargets.getOid()));
        BasicDBObject update = new BasicDBObject();
        update.put(UpdateOperators.ADDTOSET.getOperators(), new BasicDBObject("targetNames", topLevelPojoSimpleName));
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.upsert(true);
        getMongoCollection().findOneAndUpdate(find, update, options);
        createdTargets = findTheOnlyOne();
        return createdTargets.getTargetNames().contains(topLevelPojoSimpleName);
    }

    public CommonCreatedTargets findTheOnlyOne() {
        return findAll().get(0);
    }
}
