package drama.gameServer.system.jmx;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

/**
 * Created by lee on 2021/9/7
 */
public class SenderBean extends NotificationBroadcasterSupport implements MySenderListenerInterface {
    @Override
    public void sendResponse() {
        Notification notification = new Notification("all rooms info", this, System.currentTimeMillis());
        sendNotification(notification);
    }
}
