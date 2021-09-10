package drama.gameServer.system.jmx;

import javax.management.MXBean;

/**
 * Created by lee on 2021/9/7
 */
@MXBean
public interface MySenderListenerInterface {
    void sendResponse();

}
