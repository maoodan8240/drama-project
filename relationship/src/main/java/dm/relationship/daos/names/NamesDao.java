package dm.relationship.daos.names;

import ws.common.mongoDB.interfaces.BaseDao;
import dm.relationship.topLevelPojos.names.Names;

public interface NamesDao extends BaseDao<Names> {

    /**
     * @param newName
     * @return
     */
    boolean inertNewName(String newName);
}
