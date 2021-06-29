package dm.relationship.utils;

import akka.actor.ActorContext;
import dm.relationship.base.MagicWords_Mongodb;
import dm.relationship.base.MagicWords_Redis;
import dm.relationship.base.ServerEnvEnum;
import dm.relationship.base.ServerRoleEnum;
import dm.relationship.base.cluster.AkkaAddressContext;
import dm.relationship.daos.DaoContainer;
import dm.relationship.daos.centerPlayer.CenterPlayerDao;
import dm.relationship.daos.sdk.realm.OuterToInnerRealmListDao;
import dm.relationship.exception.NoGameServerCanUseException;
import dm.relationship.topLevelPojos.centerPlayer.CenterPlayer;
import dm.relationship.topLevelPojos.sdk.realm.OuterToInnerRealmList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.redis.RedisOpration;
import ws.common.redis.operation.In_RedisOperation;
import ws.common.redis.operation.RedisOprationEnum.SortedSets;
import ws.common.utils.di.GlobalInjector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChooseGameSeverUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChooseGameSeverUtils.class);
    private static final RedisOpration REDIS_OPRATION = GlobalInjector.getInstance(RedisOpration.class);

    private static final MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);
    private static final CenterPlayerDao CENTER_PLAYER_DAO = DaoContainer.getDao(CenterPlayer.class);
    private static final OuterToInnerRealmListDao OUTER_TO_INNER_REALMLIST_DAO = DaoContainer.getDao(OuterToInnerRealmList.class);

    static {
        CENTER_PLAYER_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Common);
        OUTER_TO_INNER_REALMLIST_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Common);
    }

    /**
     * 首先选择 specifiedGameServerRole
     * 其次选择Table_InnerServer_Row表里配置的
     *
     * @param actorContext
     * @param outerRealmId
     * @param specifiedGameServerRole
     * @return
     */
    public static AkkaAddressContext chooseSpecificGameServer(ActorContext actorContext, int outerRealmId, String specifiedGameServerRole) {
        OuterToInnerRealmList outerToInnerRealmList = OUTER_TO_INNER_REALMLIST_DAO.findByOuterRealmId(outerRealmId);
        List<AkkaAddressContext> serverRoleAndAddresses = new ArrayList<>();
        boolean useSpecified = !StringUtils.isBlank(specifiedGameServerRole);
        if (useSpecified) {
            serverRoleAndAddresses.add(ClusterUtils.getAddressContext(actorContext, specifiedGameServerRole));
        } else {
            serverRoleAndAddresses.add(ClusterUtils.getAddressContext(actorContext, outerToInnerRealmList.getGameServerRole()));
        }
        return chooseMinPlayerOnlineGameServer(serverRoleAndAddresses);
    }


    public static AkkaAddressContext chooseMinPlayerOnlineGameServer(ActorContext actorContext, ServerEnvEnum serverEnv) {
        return chooseMinPlayerOnlineGameServer(actorContext, serverEnv, null);
    }

    public static AkkaAddressContext chooseMinPlayerOnlineGameServer(ActorContext actorContext, ServerEnvEnum serverEnv, String specifiedGameServerRole) {
        List<AkkaAddressContext> serverRoleAndAddresses = new ArrayList<>();
        boolean useSpecified = !StringUtils.isBlank(specifiedGameServerRole);
        if (useSpecified) {
            serverRoleAndAddresses.add(ClusterUtils.getAddressContext(actorContext, specifiedGameServerRole));
        } else {
            serverRoleAndAddresses.addAll(ClusterUtils.getAddressContextLisUseEnv(actorContext, ServerRoleEnum.DM_GameServer, serverEnv));
        }
        return chooseMinPlayerOnlineGameServer(serverRoleAndAddresses);
    }

    public static AkkaAddressContext chooseMinPlayerOnlineGameServer(List<AkkaAddressContext> serverRoleAndAddresses) {
        if (serverRoleAndAddresses.size() == 0) {
            throw new NoGameServerCanUseException();
        } else if (serverRoleAndAddresses.size() == 1) {
            LOGGER.debug("集群中只有一个GameServer,直接选择使用！ Address={}", serverRoleAndAddresses.get(0).getAddress());
            return serverRoleAndAddresses.get(0);
        } else {
            try {
                Object rsObj = REDIS_OPRATION.execute(new In_RedisOperation(SortedSets.zrange.newParmBuilder().build(MagicWords_Redis.GameServerPlayerCount, 0, -1)));
                Set<String> gamserversRoleNames = SortedSets.zrange.parseResult(rsObj); // 从小到大排序
                for (String roleName : gamserversRoleNames) {
                    AkkaAddressContext addressTmp = getServerRoleAndAddresses(serverRoleAndAddresses, roleName);
                    if (addressTmp != null) {
                        return addressTmp;
                    } else {
                        REDIS_OPRATION.execute(new In_RedisOperation(SortedSets.zrem.newParmBuilder().build(MagicWords_Redis.GameServerPlayerCount, roleName)));
                        LOGGER.debug("集群中不存在roleName={}的server，Redis中key={}移除此member", roleName, MagicWords_Redis.GameServerPlayerCount);
                    }
                }
            } catch (Exception e) { // 捕获此异常
                LOGGER.error("选择玩家最小在线数量的GameServer异常！", e);
            }
            LOGGER.debug("集群中有多个GameServer, 但是Redis中不存在这些Server，默认选择第一个使用={}", serverRoleAndAddresses.get(0).getAddress());
            return serverRoleAndAddresses.get(0);
        }
    }

    private static AkkaAddressContext getServerRoleAndAddresses(List<AkkaAddressContext> serverRoleAndAddresses, String roleName) {
        for (AkkaAddressContext serverRoleAndAddress : serverRoleAndAddresses) {
            if (serverRoleAndAddress.getRoleName().equals(roleName)) {
                return serverRoleAndAddress;
            }
        }
        return null;
    }
}
