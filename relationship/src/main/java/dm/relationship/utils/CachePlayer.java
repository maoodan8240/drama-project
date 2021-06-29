package dm.relationship.utils;

import akka.actor.ActorContext;
import akka.actor.Address;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.redis.RedisOpration;
import ws.common.redis.operation.In_RedisOperation;
import ws.common.redis.operation.RedisOprationEnum.Hashes;
import ws.common.utils.di.GlobalInjector;
import dm.relationship.base.MagicWords_Redis;
import dm.relationship.base.ServerEnvEnum;
import dm.relationship.base.cluster.AkkaAddressContext;

public class CachePlayer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CachePlayer.class);
    private static final RedisOpration REDIS_OPRATION = GlobalInjector.getInstance(RedisOpration.class);

    public static void cacheToCertainServerRole(String serverRole, String playerId) {
        _add(playerId, serverRole);
    }

    public static AkkaAddressContext cacheToMinGameServer(ActorContext actorContext, ServerEnvEnum serverEnv, String specifiedGameServerRole, String playerId) {
        String serverRoleInRedis = _get(playerId);
        LOGGER.debug("playerId={} 在Redis中之前缓存的serverRole为={}", playerId, serverRoleInRedis);
        if (!StringUtils.isEmpty(serverRoleInRedis)) {
            Address addressOld = ClusterUtils.getServerAddress(actorContext, serverRoleInRedis);
            if (addressOld == null) {
                remove(playerId);
                LOGGER.debug("playerId={} 在Redis中之前缓存的serverRole为={}, 该Server目前已经从集群中移除了！", playerId, serverRoleInRedis);
            } else {
                LOGGER.debug("playerId={} 在Redis中之前缓存的serverRole为={}, 该Server目前状态正常，继续使用该Server！", playerId, serverRoleInRedis);
                return new AkkaAddressContext(serverRoleInRedis, addressOld, null);
            }
        }
        AkkaAddressContext serverRoleAndAddress = ChooseGameSeverUtils.chooseMinPlayerOnlineGameServer(actorContext, serverEnv, specifiedGameServerRole);
        _add(playerId, serverRoleAndAddress.getRoleName());
        return serverRoleAndAddress;
    }

    public static void remove(String playerId) {
        REDIS_OPRATION.execute(new In_RedisOperation(Hashes.hdel.newParmBuilder().build(MagicWords_Redis.PlayerOnWitchServerRole, playerId)));
    }

    private static void _add(String playerId, String serverRole) {
        REDIS_OPRATION.execute(new In_RedisOperation(Hashes.hset.newParmBuilder().build(MagicWords_Redis.PlayerOnWitchServerRole, playerId, serverRole)));
        LOGGER.debug("playerId={} 缓存进列服务器serverRole={}", playerId, serverRole);
    }

    private static String _get(String playerId) {
        return Hashes.hget.parseResult(REDIS_OPRATION.execute(new In_RedisOperation(Hashes.hget.newParmBuilder().build(MagicWords_Redis.PlayerOnWitchServerRole, playerId))));
    }

}
