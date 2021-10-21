package drama.gameServer.features.actor.login;

import dm.relationship.base.MagicNumbers;
import dm.relationship.base.MagicWords_Mongodb;
import dm.relationship.daos.DaoContainer;
import dm.relationship.daos.simpleId.SimpleIdDao;
import dm.relationship.enums.SimpleIdTypeEnum;
import dm.relationship.topLevelPojos.player.Player;
import dm.relationship.topLevelPojos.simpleId.SimpleId;
import drama.gameServer.features.actor.room.mc.ExtensionIniterClassHolder;
import drama.gameServer.features.actor.room.mc.extensionIniter.base.ExtCommonData;
import drama.gameServer.features.actor.room.mc.extensionIniter.base.ExtensionIniter;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.redis.RedisOpration;
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
    private static final MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);
    private static final SimpleIdDao SIMPLE_ID_DAO = DaoContainer.getDao(SimpleId.class);

    static {
        SIMPLE_ID_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Common);
    }


    public static void init() throws Exception {
        for (Class<? extends ExtensionIniter> clzz : ExtensionIniterClassHolder.getExtensionIniterClasses()) {
            extensionIniters.add(clzz.newInstance());
        }
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


    public static Player createPlayer() {
        int simpleId = SIMPLE_ID_DAO.nextSimpleId(SimpleIdTypeEnum.PLAYER);
        Player player = new Player();
        player.setPlayerId(ObjectId.get().toString());
        player.getBase().setSimpleId(simpleId);
        player.getBase().setLevel(MagicNumbers.DEFAULT_ONE);
        player.getBase().setIcon("");
        player.getAccount().setCreateAt(WsDateUtils.dateToFormatStr(new Date(), WsDateFormatEnum.yyyy_MM_dd$HH_mm_ss));
        return player;
    }
}
