package dm.relationship.daos.names;

import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.bson.Document;
import ws.common.mongoDB.implement.AbstractBaseDao;
import ws.common.mongoDB.utils.UpdateOperators;
import dm.relationship.topLevelPojos.names.Names;

public class _NamesDao extends AbstractBaseDao<Names> implements NamesDao {
    public _NamesDao() {
        super(Names.class);
    }

    @Override
    public boolean inertNewName(String newName) {
        Document find = new Document("name", newName);
        Document update = new Document(UpdateOperators.ADDTOSET.getOperators(), new Document("name", newName));
        Document rs = this.getMongoCollection().findOneAndUpdate(find, update, new FindOneAndUpdateOptions().upsert(true));
        return rs == null; // 返回null表示插入成功
    }
}
