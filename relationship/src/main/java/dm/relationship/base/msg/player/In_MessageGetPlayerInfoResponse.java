package dm.relationship.base.msg.player;

import ws.common.utils.message.implement.AbstractInnerMsg;

/**
 * Created by root on 8/24/16.
 */
public class In_MessageGetPlayerInfoResponse extends AbstractInnerMsg {
    private static final long serialVersionUID = -6148545203040676188L;

    public In_MessageGetPlayerInfoResponse(String name, int level, int combat) {
        this.name = name;
        this.level = level;
        this.combat = combat;
    }

    private String name;
    private int level;
    private int combat;


    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getCombat() {
        return combat;
    }
}
