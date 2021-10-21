package drama.gameServer.features.actor.world;

import drama.gameServer.features.actor.room.enums.RoomState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.utils.cooldown.implement.AutoClearCdList;
import ws.common.utils.cooldown.interfaces.CdList.CallbackOnExpire;

import java.util.Date;

/**
 * Created by lee on 2021/10/19
 */
public class GlobalRoomTimer {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalRoomTimer.class);
    private static final AutoClearCdList CD_LIST = new AutoClearCdList();

    public GlobalRoomTimer() {
    }

    public void addTasker(String id, RoomState actionName, Date expireDate, CallbackOnExpire callback) {
        CD_LIST.add(id + "_" + actionName.getName(), expireDate);
        CD_LIST.setCallbackOnExpire(callback);
    }

    public void removeTasker(String id, RoomState actionName) {
        String typeName = id + "_" + actionName.getName();
        if (CD_LIST.has(typeName)) {
            CD_LIST.clear(typeName);
        }
    }

}
