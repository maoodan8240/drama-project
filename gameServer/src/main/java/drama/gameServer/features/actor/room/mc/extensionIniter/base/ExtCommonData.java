package drama.gameServer.features.actor.room.mc.extensionIniter.base;

import dm.relationship.base.ServerRoleEnum;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.db.saver.In_Game_Hashes_OneId_MultiPojos_SameOutRealmId_Saver;
import dm.relationship.noDbPojo.common.RoomPlayerCreateTargets;
import dm.relationship.noDbPojo.interfaces.NoDbPojo;
import dm.relationship.topLevelPojos.common.PlayerCreatedTargets;
import dm.relationship.topLevelPojos.player.Player;
import dm.relationship.utils.ActorMsgSynchronizedExecutor;
import drama.gameServer.features.actor.cluster.ClusterListener;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.redis.RedisOpration;
import ws.common.utils.di.GlobalInjector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtCommonData {
    private static final RedisOpration REDIS_OPRATION = GlobalInjector.getInstance(RedisOpration.class);
    private Map<Class<? extends TopLevelPojo>, TopLevelPojo> allPojo = new HashMap<>();
    private Map<Class<? extends NoDbPojo>, NoDbPojo> allNoDbPojo = new HashMap<>();
    private RoomPlayerCreateTargets roomPlayerCreateTargets;
    private PlayerCreatedTargets createdTargets;
    private Player player;
    private RoomPlayer roomPlayer;

    public ExtCommonData(Player player) {
        this.player = player;
        this.allPojo.put(Player.class, player);
    }

    public ExtCommonData(RoomPlayer roomPlayer) {
        this.roomPlayer = roomPlayer;
        this.allNoDbPojo.put(RoomPlayer.class, roomPlayer);
    }

    // =========================== 操作方法 ===========================


    public void addPojo(TopLevelPojo topLevelPojo) {
        allPojo.put(topLevelPojo.getClass(), topLevelPojo);
        addToPlayerCreatedTargets(createdTargets, topLevelPojo);
    }

    public void addNoDbPojo(NoDbPojo pojo) {
        allNoDbPojo.put(pojo.getClass(), pojo);
//        addToRoomPlayerCreateTargets(roomPlayerCreateTargets, pojo);
    }

    public <T extends TopLevelPojo> T getPojo(Class<T> tClass) {
        return (T) this.allPojo.get(tClass);
    }

    public <T extends NoDbPojo> T getNoPojo(Class<T> tClass) {
        return (T) this.allNoDbPojo.get(tClass);
    }

    public void clear() {
        this.player = null;
        this.allPojo.clear();
        this.allNoDbPojo.clear();
    }

    public void saveAll() {
        List<TopLevelPojo> toSave = new ArrayList<>();
        toSave.addAll(allPojo.values());
        int outerRealmId = player.getAccount().getOuterRealmId();
        In_Game_Hashes_OneId_MultiPojos_SameOutRealmId_Saver.Request request = new In_Game_Hashes_OneId_MultiPojos_SameOutRealmId_Saver.Request(outerRealmId, toSave);
        ActorMsgSynchronizedExecutor.sendMsgToSingleServerIgnoreEnv(ServerRoleEnum.DM_MongodbRedisServer, ClusterListener.getActorContext(), ActorSystemPath.DM_MongodbRedisServer_Selection_PojoSaver, request);
    }

    public static void addToPlayerCreatedTargets(PlayerCreatedTargets createdTargets, TopLevelPojo topLevelPojo) {
        String simpleName = topLevelPojo.getClass().getSimpleName();
        if (!createdTargets.getTargetNames().contains(simpleName)) {
            createdTargets.getTargetNames().add(simpleName);
        }
    }

    public static void addToRoomPlayerCreateTargets(RoomPlayerCreateTargets roomPlayerCreateTargets, NoDbPojo noDbPojo) {
        String simpleName = noDbPojo.getClass().getSimpleName();
        if (!roomPlayerCreateTargets.getTargetNames().contains(simpleName)) {
            roomPlayerCreateTargets.getTargetNames().add(simpleName);
        }
    }


    // =========================== getter setter ===========================


    public Player getPlayer() {
        return player;
    }

    public RoomPlayer getRoomPlayer() {
        return roomPlayer;
    }

    public RoomPlayerCreateTargets getRoomPlayerCreateTargets() {
        return roomPlayerCreateTargets;
    }

    public void setRoomPlayerCreateTargets(RoomPlayerCreateTargets roomPlayerCreateTargets) {
        this.roomPlayerCreateTargets = roomPlayerCreateTargets;
    }

    public PlayerCreatedTargets getCreatedTargets() {
        return createdTargets;
    }

    public void setCreatedTargets(PlayerCreatedTargets createdTargets) {
        this.createdTargets = createdTargets;
    }

}
