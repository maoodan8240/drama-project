package drama.gameServer.features.actor.world.ctrl;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import dm.relationship.daos.player.PlayerDao;
import drama.gameServer.features.actor.world.pojo.World;
import ws.common.network.server.interfaces.Connection;
import ws.common.utils.mc.controler.Controler;

import java.util.List;

public interface WorldCtrl extends Controler<World> {

    void setActorContext(ActorContext actorContext);

    ActorRef getPlayerActorRef(String playerId);

    void removePlayerActorRef(String playerId);


    boolean containsPlayerActorRef(String playerId);

    PlayerDao getPlayerDao();

    void addRoomActorRef(String roomId, ActorRef roomActorRef);

    ActorRef getRoomActorRef(String roomId);

    void removeRoomActorRef(String roomId);

    void addPlayerIdAndActor(String playerId, String mobileNum, ActorRef playerActorRef);

    boolean roomActorCanUse(String roomId);

    void getGameServerPlayerNum();

    /**
     * 设置玩家离线
     *
     * @param playerId
     */
    void setOffline(String playerId);

    /**
     * 添加玩家id、conn、安全码、安全码的时间
     *
     * @param playerId
     * @param conn
     */
    void login(String playerId, Connection conn);

    /**
     * 玩家彻底推出游戏,从内存中移除
     *
     * @param playerId
     */
    void logout(String playerId);

    /**
     * 玩家有心跳
     *
     * @param playerId
     */
    void setHeartBeating(String playerId);

    /**
     * 玩家重连之前清除玩家的老数据
     *
     * @param playerId
     */
    void beforeReconn(String playerId);

    /**
     * <pre>
     * 针对 PlayerOnlineActor 是否可用
     *    PlayerOnlineActor 不存在或则存在且没有进行kill则可用true
     *    PlayerOnlineActor 进行了kill,在kill那一刻到完全被kill之前的这段时间为不可用状态false
     * </pre>
     *
     * @param playerId
     * @return
     */
    boolean canUse(String playerId);

    /**
     * 通过 conn 获取玩家id
     *
     * @param conn
     * @return
     */
    String getPlayerId(Connection conn);

    /**
     * 通过玩家获取 conn
     *
     * @param playerId
     * @return
     */
    Connection getConnection(String playerId);

    /**
     * 设置 PlayerOnlineActor不可用
     *
     * @param playerId
     */
    void setCanNotUse(String playerId);

    /**
     * 获取所有超时的玩家
     *
     * @return
     */
    List<String> getAllOverTimeCachePalyerOnlineActor(int time);

    /**
     * <pre>
     * 是否存在 conn
     *   在线、缓存状态为true
     *   离线为状态为false
     * </pre>
     *
     * @param conn
     * @return
     */
    boolean contains(Connection conn);
}
