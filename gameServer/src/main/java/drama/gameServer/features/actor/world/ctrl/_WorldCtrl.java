package drama.gameServer.features.actor.world.ctrl;

import akka.actor.ActorRef;
import akka.actor.Kill;
import dm.relationship.base.MagicWords_Mongodb;
import dm.relationship.daos.player.PlayerDao;
import drama.gameServer.features.actor.world.pojo.World;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.utils.di.GlobalInjector;
import ws.common.utils.mc.controler.AbstractControler;
import ws.common.utils.message.interfaces.PrivateMsg;

public class _WorldCtrl extends AbstractControler<World> implements WorldCtrl {
    private static final PlayerDao PLAYER_DAO = GlobalInjector.getInstance(PlayerDao.class);
    private static final MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);

    static {
        PLAYER_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Common);
    }

    @Override
    public void sendPrivateMsg(PrivateMsg privateMsg) {

    }

    private void add(String playerId, String mobileNum) {
        target.getPlayerIdToMobileNum().put(playerId, mobileNum);
    }


    private void addPlayerActorRef(String playerId, ActorRef playerActorRef) {
        target.getPlayerIdToPlayerActorRef().put(playerId, playerActorRef);
    }


    @Override
    public ActorRef getPlayerActorRef(String playerId) {
        return target.getPlayerIdToPlayerActorRef().get(playerId);
    }


    @Override
    public void remove(String playerId) {
        if (playerId != null) {
            if (target.getPlayerIdToMobileNum().containsKey(playerId)) {
                target.getPlayerIdToMobileNum().remove(playerId);
            }
            if (target.getPlayerIdToPlayerActorRef().containsKey(playerId)) {
                target.getPlayerIdToPlayerActorRef().get(playerId).tell(Kill.getInstance(), ActorRef.noSender());
                target.getPlayerIdToPlayerActorRef().remove(playerId);
            }
        }
    }


    @Override
    public boolean playerActorCanUse(String playerId) {
        return getPlayerActorRef(playerId) != null;
    }

    public PlayerDao getPlayerDao() {
        return PLAYER_DAO;
    }

    @Override
    public void putRoomActorRef(String roomId, ActorRef roomActorRef) {
        this.target.getRoomIdToRoomActorRef().put(roomId, roomActorRef);
    }


    @Override
    public ActorRef getRoomActorRef(String roomId) {
        return this.target.getRoomIdToRoomActorRef().get(roomId);
    }


    @Override
    public void killRoomActor(String roomId) {
        getRoomActorRef(roomId).tell(Kill.getInstance(), ActorRef.noSender());
    }

    @Override
    public void addPlayerIdAndActor(String playerId, String mobileNum, ActorRef playerActorRef) {
        addPlayerActorRef(playerId, playerActorRef);
        add(playerId, mobileNum);
    }

    @Override
    public boolean roomActorCanUse(String roomId) {
        return this.getRoomActorRef(roomId) != null;
    }


}
