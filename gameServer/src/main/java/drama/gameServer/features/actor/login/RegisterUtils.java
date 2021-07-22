package drama.gameServer.features.actor.login;

import dm.relationship.base.MagicNumbers;
import dm.relationship.topLevelPojos.TopLevelPojoContainer;
import dm.relationship.topLevelPojos.centerPlayer.CenterPlayer;
import dm.relationship.topLevelPojos.player.Player;
import drama.gameServer.features.actor.login.ext.base.ExtCommonData;
import drama.gameServer.features.actor.login.ext.base.ExtensionIniter;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.redis.RedisOpration;
import ws.common.redis.operation.In_RedisOperation;
import ws.common.redis.operation.RedisOprationEnum.Hashes;
import ws.common.utils.date.WsDateFormatEnum;
import ws.common.utils.date.WsDateUtils;
import ws.common.utils.di.GlobalInjector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegisterUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterUtils.class);
    private static final List<ExtensionIniter> extensionIniters = new ArrayList<>();
    private static final RedisOpration REDIS_OPRATION = GlobalInjector.getInstance(RedisOpration.class);

    public static void init() throws Exception {
        for (Class<? extends ExtensionIniter> clzz : ExtensionIniterClassHolder.getExtensionIniterClasses()) {
            extensionIniters.add(clzz.newInstance());
        }
    }

    public static boolean register(CenterPlayer centerPlayer) {
        Player player = createPlayer(centerPlayer);
        centerPlayer.setGameId(player.getPlayerId());
        _initAllExtensions(player);
        boolean rs = !StringUtils.isEmpty(Hashes.hget.parseResult(
                REDIS_OPRATION.execute(
                        new In_RedisOperation(Hashes.hget.newParmBuilder().build(
                                TopLevelPojoContainer.getGameRedisKey(Player.class, player.getAccount().getOuterRealmId()), player.getPlayerId())
                        ))));
        if (rs) {
            LOGGER.info("注册GameServer玩家, playerId={}, playerName={} 成功！", centerPlayer.getCenterId(), centerPlayer.getPlayerName());
//            LogHandler.playerLoginLog(player, "");
        } else {
            LOGGER.error("注册GameServer玩家, playerId={}, playerName={} 失败！redis 查询是失败！ GameId={} .", centerPlayer.getCenterId(), centerPlayer.getPlayerName(), player.getPlayerId());
        }
        return rs;
    }

    private static void _initAllExtensions(Player player) {
        ExtCommonData commonDataIniter = new ExtCommonData(player);
        for (ExtensionIniter initer : extensionIniters) {
            try {
                initer.init(commonDataIniter);
            } catch (Exception e) {
                LOGGER.error("PlayerExtensionIniter init {} error !", initer.getClass().toString(), e);
            }
        }
        commonDataIniter.saveAll();
        commonDataIniter.clear();
    }

    private static Player createPlayer(CenterPlayer centerPlayer) {
        String playerName = centerPlayer.getPlayerName();
        Player player = new Player();
        player.setPlayerId(ObjectId.get().toString());
        player.getBase().setSimpleId(centerPlayer.getSimpleId());
        player.getBase().setLevel(MagicNumbers.DEFAULT_ONE);
        player.getBase().setName(playerName);
        player.getBase().setSex(centerPlayer.getSex());

        player.getBase().setIcon("");
        player.getAccount().setOuterRealmId(centerPlayer.getOuterRealmId());
        player.getAccount().setPlatformType(centerPlayer.getPlatformType());
        player.getAccount().setSubPlatform(centerPlayer.getSubPlatform());
        player.getAccount().setPlatformUid(centerPlayer.getPlatformUid());
        player.getAccount().setCreateAt(WsDateUtils.dateToFormatStr(new Date(), WsDateFormatEnum.yyyy_MM_dd$HH_mm_ss));
        return player;
    }
}
