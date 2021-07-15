package drama.gameServer.features.manager;

import dm.relationship.table.RootTc;

public class AppDebugger implements AppDebuggerMBean {
    @Override
    public String allManager(String actionName, String funcName, String jsonStr) {
        try {
            RootTc.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return funcName;
    }
}
