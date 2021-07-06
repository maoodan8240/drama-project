package drama.gameServer.features.actor.world.ctrl;

import akka.actor.ActorRef;
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
    public void removePlayerActorRef(String playerId) {
        //只remove关联,具体的killActor还是在他自己的actor接到消息是执行
        if (playerId != null) {
            if (target.getPlayerIdToMobileNum().containsKey(playerId)) {
                target.getPlayerIdToMobileNum().remove(playerId);
            }
            if (target.getPlayerIdToPlayerActorRef().containsKey(playerId)) {
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
    public void addRoomActorRef(String roomId, ActorRef roomActorRef) {
        this.target.getRoomIdToRoomActorRef().put(roomId, roomActorRef);
    }


    @Override
    public ActorRef getRoomActorRef(String roomId) {
        return this.target.getRoomIdToRoomActorRef().get(roomId);
    }


    @Override
    public void removeRoomActorRef(String roomId) {
        //只remove关联,具体的killActor还是在他自己的actor接到消息是执行
        this.target.getRoomIdToRoomActorRef().remove(roomId);
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
