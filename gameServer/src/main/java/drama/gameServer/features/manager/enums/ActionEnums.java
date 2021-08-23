package drama.gameServer.features.manager.enums;

public enum ActionEnums {
    FRESH_TABLE_DATE("fresh_table_data"),//
    KILL_ROOM("kill_room"),//
    NULL("");//

    private String actionName;

    ActionEnums(String actionName) {
        this.actionName = actionName;
    }

    public String getActionName() {
        return actionName;
    }
}
