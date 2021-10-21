package drama.gameServer.features.extp.itemBag.msg;

import dm.relationship.base.IdMaptoCount;
import ws.common.utils.message.interfaces.PrivateMsg;

/**
 * Created by lee on 2021/10/11
 */
public class Pr_InitPropMsg implements PrivateMsg {
    private IdMaptoCount idMaptoCount;

    public Pr_InitPropMsg(IdMaptoCount prop) {
        this.idMaptoCount = prop;
    }

    public IdMaptoCount getIdMaptoCount() {
        return idMaptoCount;
    }
}
