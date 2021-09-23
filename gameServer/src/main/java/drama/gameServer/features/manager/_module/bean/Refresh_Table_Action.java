package drama.gameServer.features.manager._module.bean;

import dm.relationship.table.RootTc;
import drama.gameServer.features.manager.Action;
import ws.common.table.table.interfaces.Row;

import javax.management.NotificationBroadcasterSupport;
import java.util.List;

/**
 * Created by lee on 2021/9/8
 */
public class Refresh_Table_Action extends NotificationBroadcasterSupport implements Action {
    @Override
    public String handle(String funcName, String args) {
        String rs = "not_excute";
        try {
            List<Class<? extends Row>> refresh = RootTc.refresh();
            rs = "true";
        } catch (Exception e) {
            e.printStackTrace();
            rs = "false";
        } finally {
            return rs;
        }
    }
}
