package drama.gameServer.features.extp.itemBag.msg;

import dm.relationship.base.msg.interfaces.RoomInnerExtpMsg;
import dm.relationship.enums.item.IdItemTypeEnum;
import drama.gameServer.features.extp.itemBag.pojo.PlainCell;
import drama.gameServer.features.extp.itemBag.pojo.SpecialCell;

/**
 * Created by lee on 2021/10/13
 */
public class In_RoomGiveItemMsg implements RoomInnerExtpMsg {
    private SpecialCell specialCell;
    private PlainCell plainCell;
    private int beRoleId;
    private String selfRoleName;
    private IdItemTypeEnum itemType;


    public In_RoomGiveItemMsg(SpecialCell specialCell, int beRoleId, String selfRoleName, IdItemTypeEnum itemType) {
        this.specialCell = specialCell;
        this.beRoleId = beRoleId;
        this.selfRoleName = selfRoleName;
        this.itemType = itemType;
    }

    public In_RoomGiveItemMsg(PlainCell plainCell, int beRoleId, String selfRoleName, IdItemTypeEnum itemType) {
        this.plainCell = plainCell;
        this.beRoleId = beRoleId;
        this.selfRoleName = selfRoleName;
        this.itemType = itemType;
    }


    public SpecialCell getSpecialCell() {
        return specialCell;
    }

    public PlainCell getPlainCell() {
        return plainCell;
    }

    public int getBeRoleId() {
        return beRoleId;
    }

    public String getSelfRoleName() {
        return selfRoleName;
    }

    public IdItemTypeEnum getItemType() {
        return itemType;
    }
}
