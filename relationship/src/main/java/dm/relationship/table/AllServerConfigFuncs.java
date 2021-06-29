package dm.relationship.table;

import ws.common.utils.di.GlobalInjector;
import ws.common.utils.formula.FormulaContainer;
import ws.common.utils.formula.FormulaContainerUtils;

public class AllServerConfigFuncs {
    private static FormulaContainer formulaContainer = GlobalInjector.getInstance(FormulaContainer.class);


    // 公式计算：星级判定
    private static final String FuncName_Pve_StarLevel_Judge = FormulaContainerUtils.autoGenerateFuncName(AllServerConfigFuncs.class, "Pve_StarLevel_Judge", AllServerConfig.Pve_StarLevel_Judge.getId());
    

    public static int parsePveStarLevel(int heroDieNum, int enterHeroNum) {
        if (!formulaContainer.contains(FuncName_Pve_StarLevel_Judge)) {
//            formulaContainer.addFunc(FuncName_Pve_StarLevel_Judge, AllServerConfig.Pve_StarLevel_Judge.getConfig());
        }
        return FormulaContainerUtils.convertToInt(formulaContainer.invokeFunction(FuncName_Pve_StarLevel_Judge, new String[]{"HeroDieNum", "EnterHeroNum"}, new Object[]{heroDieNum, enterHeroNum}), 0);
    }
}