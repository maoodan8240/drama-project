package drama.gameServer.features.extp.itemBag.msg;

import dm.relationship.base.msg.interfaces.RoomInnerExtpMsg;
import drama.gameServer.features.extp.itemBag.pojo.SpecialCell;

/**
 * Created by lee on 2021/10/12
 */
public class In_RoomShowItemMsg implements RoomInnerExtpMsg {
    private SpecialCell specialCell;
    private int beRoleId;
    private String selfRoleName;

    public In_RoomShowItemMsg(SpecialCell specialCell, int beRoleId, String selfRoleName) {
        this.specialCell = specialCell;
        this.beRoleId = beRoleId;
        this.selfRoleName = selfRoleName;
    }

    public SpecialCell getSpecialCell() {
        return specialCell;
    }

    public int getBeRoleId() {
        return beRoleId;
    }

    public String getSelfRoleName() {
        return selfRoleName;
    }
}
