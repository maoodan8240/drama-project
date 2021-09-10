package drama.gameServer.features.manager;

import drama.gameServer.features.manager._module.bean.Kill_Room_Action;
import drama.gameServer.features.manager._module.bean.Refresh_Table_Action;
import drama.gameServer.features.manager._module.bean.Sync_Room_Action;

public enum ActionEnums {
    REFRESH_TABLE_DATE(new Refresh_Table_Action(), "refresh_table_data"),//
    KILL_ROOM(new Kill_Room_Action(), "kill_room"),//
    SYNC_ROOM(new Sync_Room_Action(), "sync_room"),//
    ;//
    private Action action;
    private String actionName;

    private ActionEnums(Action action, String actionName) {
        this.action = action;
        this.actionName = actionName;
    }

    public String getActionName() {
        return actionName;
    }

    public Action getAction() {
        return action;
    }

    public static String handle(String acitonName, String funcName, String args) {
        for (ActionEnums actionEnums : values()) {
            if (actionEnums.getActionName().equals(acitonName)) {
                return actionEnums.getAction().handle(funcName, args);
            }
        }
        return null;
    }

}
