package drama.gameServer.features.manager;

public class AppDebugger implements AppDebuggerMBean {
    @Override
    public String allManager(String actionName, String funcName, String jsonStr) {
        String handle_result = ActionEnums.handle(actionName, funcName, jsonStr);
        return handle_result;
    }


}
