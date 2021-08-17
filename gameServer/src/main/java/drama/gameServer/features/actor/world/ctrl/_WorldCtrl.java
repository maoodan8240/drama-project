package drama.gameServer.features.actor.world.ctrl;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Kill;
import dm.relationship.base.MagicWords_Mongodb;
import dm.relationship.daos.player.PlayerDao;
import drama.gameServer.features.actor.world.pojo.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.network.server.interfaces.Connection;
import ws.common.utils.di.GlobalInjector;
import ws.common.utils.mc.controler.AbstractControler;
import ws.common.utils.message.interfaces.PrivateMsg;

import java.util.ArrayList;
import java.util.List;

public class _WorldCtrl extends AbstractControler<World> implements WorldCtrl {
    private static final Logger LOGGER = LoggerFactory.getLogger(_WorldCtrl.class);
    private static final PlayerDao PLAYER_DAO = GlobalInjector.getInstance(PlayerDao.class);
    private static final MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);
    private ActorContext actorContext;

    static {
        PLAYER_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Common);
    }

    @Override
    public void sendPrivateMsg(PrivateMsg privateMsg) {

    }

    private void add(String playerId, String mobileNum) {
        target.getPlayerIdToPlayerName().put(playerId, mobileNum);
    }


    private void addPlayerActorRef(String playerId, ActorRef playerActorRef) {
        target.getPlayerIdToPlayerActorRef().put(playerId, playerActorRef);
    }


    @Override
    public void setActorContext(ActorContext actorContext) {
        this.actorContext = actorContext;
    }

    @Override
    public String getPlayerNameById(String playerId) {
        return target.getPlayerIdToPlayerName().get(playerId);
    }

    @Override
    public ActorRef getPlayerActorRef(String playerId) {
        return target.getPlayerIdToPlayerActorRef().get(playerId);
    }


    @Override
    public void removePlayerActorRef(String playerId) {
        //只remove关联,具体的killActor还是在他自己的actor接到消息是执行
        if (playerId != null) {
            if (target.getPlayerIdToPlayerName().containsKey(playerId)) {
                target.getPlayerIdToPlayerName().remove(playerId);
            }
            if (target.getPlayerIdToPlayerActorRef().containsKey(playerId)) {
                target.getPlayerIdToPlayerActorRef().remove(playerId);
            }
        }
    }


    @Override
    public boolean containsPlayerActorRef(String playerId) {
        return target.getPlayerIdToPlayerActorRef().containsKey(playerId);
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

    @Override
    public void getGameServerPlayerNum() {
        int sumOnline = 0;
        for (String s : target.getPlayerIdToPlayerActorRef().keySet()) {

        }
    }

    @Override
    public void setOffline(String playerId) {
        target.getIdToOffline().put(playerId, true);
        target.getIdToOfflineTime().put(playerId, System.currentTimeMillis());
    }

    @Override
    public void login(String playerId, Connection conn) {
        target.getConnToId().put(conn, playerId);
        target.getIdToConn().put(playerId, conn);

        target.getIdToCodeTime().put(playerId, System.currentTimeMillis());
    }

    @Override
    public void logout(String playerId) {
        target.getPlayerIdToPlayerActorRef().remove(playerId);
        target.getIdToCanUse().remove(playerId);

        target.getIdToOffline().remove(playerId);
        target.getIdToOfflineTime().remove(playerId);
        target.getPlayerIdToPlayerName().remove(playerId);
        Connection conn = target.getIdToConn().remove(playerId);
        if (conn != null) {
            conn.close();
            target.getConnToId().remove(conn);
        }
    }

    @Override
    public void setHeartBeating(String playerId) {
        target.getIdToOffline().remove(playerId);
        target.getIdToOfflineTime().remove(playerId);
    }

    @Override
    public void beforeReconn(String playerId) {
        // 如果存在旧的连接，则移除且断开
        target.getIdToOffline().remove(playerId);
        target.getIdToOfflineTime().remove(playerId);

        Connection conn = target.getIdToConn().remove(playerId);
        if (conn != null) {
            conn.close();
            target.getConnToId().remove(conn);
        }
    }

    @Override
    public boolean canUse(String playerId) {
        if (!target.getIdToCanUse().keySet().contains(playerId)) {
            return true;
        }
        return target.getIdToCanUse().get(playerId);
    }

    @Override
    public String getPlayerId(Connection conn) {
        return target.getConnToId().get(conn);
    }

    @Override
    public Connection getConnection(String playerId) {
        return target.getIdToConn().get(playerId);
    }

    @Override
    public void setCanNotUse(String playerId) {
        target.getIdToCanUse().put(playerId, false);
    }

    @Override
    public List<String> getAllOverTimeCachePalyerOnlineActor(int time) {
        long curTime = System.currentTimeMillis();
        long coverTime = curTime - time * 60 * 1000;
        List<String> playerIds = new ArrayList<>();
        target.getIdToOffline().keySet().forEach(playerId -> {
            if (target.getIdToOffline().get(playerId)) {
                long overAt = target.getIdToOfflineTime().get(playerId);
                if (overAt <= coverTime) {
                    playerIds.add(playerId);
                }
            }
        });
        return playerIds;
    }

    @Override
    public boolean contains(Connection conn) {
        return target.getConnToId().keySet().contains(conn);
    }

    @Override
    public void beginLogout(String playerId, WorldCtrl worldCtrl, ActorRef self) {
        worldCtrl.setCanNotUse(playerId);
        worldCtrl.getPlayerActorRef(playerId).tell(Kill.getInstance(), self);
    }

    @Override
    public boolean containsRoom(String roomId) {
        return target.getRoomIdToRoomActorRef().containsKey(roomId);
    }
}
