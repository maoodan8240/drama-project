package dm.relationship.daos.playerCreatedTargets;

import ws.common.mongoDB.implement.AbstractBaseDao;
import dm.relationship.topLevelPojos.common.PlayerCreatedTargets;

public class _PlayerCreatedTargetsDao extends AbstractBaseDao<PlayerCreatedTargets> implements PlayerCreatedTargetsDao {

    public _PlayerCreatedTargetsDao() {
        super(PlayerCreatedTargets.class);
    }
}
