package drama.gameServer.features.actor.room.mc.extensionIniter;

import drama.gameServer.features.actor.room.mc.ExtensionIniterClassHolder;
import drama.gameServer.features.actor.room.mc.extensionIniter.base.ExtCommonData;
import drama.gameServer.features.actor.room.mc.extensionIniter.base.ExtensionIniter;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2021/10/8
 */
public class ExtIniterUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtIniterUtils.class);
    private static final List<ExtensionIniter> extensionIniters = new ArrayList<>();

    public static void init() throws Exception {
        for (Class<? extends ExtensionIniter> clzz : ExtensionIniterClassHolder.getExtensionIniterClasses()) {
            extensionIniters.add(clzz.getDeclaredConstructor().newInstance());
        }
    }

    public static void initAllExtensions(RoomPlayer roomPlayer) {
        LOGGER.debug("开始初始化RoomPlayerExtp....");
        ExtCommonData commonDataIniter = new ExtCommonData(roomPlayer);
        for (ExtensionIniter initer : extensionIniters) {
            try {
                initer.init(commonDataIniter);
            } catch (Exception e) {
                LOGGER.error("RoomPlayerExtensionIniter init {} error !", initer.getClass().toString(), e);
            }
        }
        commonDataIniter.clear();
    }
}
