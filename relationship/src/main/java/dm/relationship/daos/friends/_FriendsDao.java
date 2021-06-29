package dm.relationship.daos.friends;

import ws.common.mongoDB.implement.AbstractBaseDao;
import dm.relationship.topLevelPojos.friends.Friends;

public class _FriendsDao extends AbstractBaseDao<Friends> implements FriendsDao {

    public _FriendsDao() {
        super(Friends.class);
    }
}
