package drama.gameServer.features.extp.itemBag.msg;

import dm.relationship.base.msg.interfaces.RoomInnerExtpMsg;
import dm.relationship.enums.item.IdItemTypeEnum;
import drama.gameServer.features.extp.itemBag.pojo.PlainCell;
import drama.gameServer.features.extp.itemBag.pojo.SpecialCell;

import java.util.List;

/**
 * Created by lee on 2021/10/25
 */
public class In_RoomDeadDropItemMsg implements RoomInnerExtpMsg {
    private List<SpecialCell> specialCell;
    private PlainCell plainCell;
    private int beRoleId;
    private String selfRoleName;
    private IdItemTypeEnum itemType;

    /**
     * @param specialCell
     * @param beRoleId
     * @param selfRoleName
     * @param itemType
     */
    public In_RoomDeadDropItemMsg(List<SpecialCell> specialCell, int beRoleId, String selfRoleName, IdItemTypeEnum itemType) {
        this.specialCell = specialCell;
        this.beRoleId = beRoleId;
        this.selfRoleName = selfRoleName;
        this.itemType = itemType;
    }

    /**
     * @param plainCell
     * @param beRoleId
     * @param selfRoleName
     * @param itemType
     */
    public In_RoomDeadDropItemMsg(PlainCell plainCell, int beRoleId, String selfRoleName, IdItemTypeEnum itemType) {
        this.plainCell = plainCell;
        this.beRoleId = beRoleId;
        this.selfRoleName = selfRoleName;
        this.itemType = itemType;
    }

    @Override
    public int getBeRoleId() {
        return beRoleId;
    }

    public String getSelfRoleName() {
        return selfRoleName;
    }

    public IdItemTypeEnum getItemType() {
        return itemType;
    }

    public List<SpecialCell> getSpecialCell() {
        return specialCell;
    }

    public PlainCell getPlainCell() {
        return plainCell;
    }
}
