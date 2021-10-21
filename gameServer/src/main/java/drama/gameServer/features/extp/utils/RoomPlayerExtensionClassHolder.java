package drama.gameServer.features.extp.utils;

import dm.relationship.exception.BusinessLogicMismatchConditionException;
import drama.gameServer.features.actor.room.mc.extension.RoomPlayerExtension;
import drama.gameServer.features.extp.RoomPlayerExtpPackageHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.utils.classProcess.ClassFinder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2021/9/30
 */
public class RoomPlayerExtensionClassHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomPlayerExtensionClassHolder.class);

    private static List<Class<? extends RoomPlayerExtension>> roomPlayerAllExtensionClasses = null;
    private static List<Class<? extends RoomPlayerExtension>> roomPlayerUseExtensionClasses = new ArrayList<>();

    static {
        roomPlayerAllExtensionClasses = ClassFinder.getAllAssignedClass(RoomPlayerExtension.class, RoomPlayerExtpPackageHolder.class);
        init();
    }

    public static void init() {
        roomPlayerUseExtensionClasses.clear();
        for (Class<? extends RoomPlayerExtension> extensionClass : roomPlayerAllExtensionClasses) {
            if (useExtension(extensionClass)) {
                roomPlayerUseExtensionClasses.add(extensionClass);
            } else {
                LOGGER.debug("roomPlayerExtension={} 未启用！开启请修改[useExtension] 属性!", extensionClass);
            }
        }
    }

    public static List<Class<? extends RoomPlayerExtension>> getRoomPlayerUseExtensionClasses() {
        return new ArrayList<>(roomPlayerUseExtensionClasses);
    }


    private static boolean useExtension(Class<? extends RoomPlayerExtension> extensionClass) {
        try {
            Field field = extensionClass.getField("useExtension");
            return (Boolean) field.get(null);
        } catch (Exception e) {
            String msg = String.format("clazz=%s 未配置 [useExtension] 属性!", extensionClass);
            throw new BusinessLogicMismatchConditionException(msg);
        }


    }
}
