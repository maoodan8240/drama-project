package drama.gameServer.features.actor.login.ext.base;

import dm.relationship.base.ServerRoleEnum;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.db.saver.In_Game_Hashes_OneId_MultiPojos_SameOutRealmId_Saver;
import dm.relationship.topLevelPojos.common.PlayerCreatedTargets;
import dm.relationship.topLevelPojos.player.Player;
import dm.relationship.utils.ActorMsgSynchronizedExecutor;
import drama.gameServer.features.actor.cluster.ClusterListener;
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
    private PlayerCreatedTargets createdTargets;
    private Player player;
    private int heroId1;
    private int heroId2;

    public ExtCommonData(Player player) {
        this.player = player;
        this.allPojo.put(Player.class, player);
    }


    // =========================== 操作方法 ===========================


    public void addPojo(TopLevelPojo topLevelPojo) {
        allPojo.put(topLevelPojo.getClass(), topLevelPojo);
        addToPlayerCreatedTargets(createdTargets, topLevelPojo);
    }

    public <T extends TopLevelPojo> T getPojo(Class<T> tClass) {
        return (T) this.allPojo.get(tClass);
    }

    public void clear() {
        this.player = null;
        this.allPojo.clear();
        this.heroId1 = 0;
        this.heroId2 = 0;
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


    // =========================== getter setter ===========================

    public int getHeroId1() {
        return heroId1;
    }

    public void setHeroId1(int heroId1) {
        this.heroId1 = heroId1;
    }

    public int getHeroId2() {
        return heroId2;
    }

    public void setHeroId2(int heroId2) {
        this.heroId2 = heroId2;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerCreatedTargets getCreatedTargets() {
        return createdTargets;
    }

    public void setCreatedTargets(PlayerCreatedTargets createdTargets) {
        this.createdTargets = createdTargets;
    }

}
