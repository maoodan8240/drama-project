package drama.gameServer.features.manager._module.interfaces;

import javax.management.Notification;
import javax.management.NotificationListener;

/**
 * Created by lee on 2021/9/8
 */
public class Refresh_Table_Listener implements NotificationListener {
    @Override
    public void handleNotification(Notification notification, Object o) {
        //TODO sendHttpResponse
        System.out.println("Refresh_Table_Listener 回消息");
    }
}
