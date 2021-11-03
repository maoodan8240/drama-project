package drama.gameServer.features.extp.utils;

import drama.gameServer.features.actor.room.ctrl.RoomCtrl;
import drama.gameServer.features.actor.room.ctrl.RoomPlayerCtrl;
import drama.gameServer.features.actor.room.mc.extension.RoomPlayerExtension;

/**
 * Created by lee on 2021/9/29
 */
public class LoadAllRoomPlayerExtensions {
    public static void loadAll(RoomPlayerCtrl roomPlayerCtrl, RoomCtrl roomCtrl) {
        try {
            for (Class<? extends RoomPlayerExtension> extensionClass : RoomPlayerExtensionClassHolder.getRoomPlayerUseExtensionClasses()) {
                _addExtension(roomPlayerCtrl, roomCtrl, extensionClass);
            }
        } catch (Exception e) {

        }
    }

    private static void _addExtension(RoomPlayerCtrl roomPlayerCtrl, RoomCtrl roomCtrl, Class<? extends RoomPlayerExtension> clzz) throws Exception {
        RoomPlayerExtension<?> extension = clzz.getConstructor(RoomPlayerCtrl.class, RoomCtrl.class).newInstance(roomPlayerCtrl, roomCtrl);
        roomPlayerCtrl.addExtension(extension);
    }
}
