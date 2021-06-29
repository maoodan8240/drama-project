package dm.relationship.topLevelPojos.itemBag;

import dm.relationship.topLevelPojos.PlayerTopLevelPojo;

import java.util.HashMap;
import java.util.Map;

public class ItemBag extends PlayerTopLevelPojo {
    private static final long serialVersionUID = -5016969318074016812L;

    private int maxIdSeq; // itemId 最大的顺序号
    private Map<Integer, PlainCell> tpIdToPlainCell = new HashMap<>(); // 普通物品
    private Map<Integer, SpecialCell> idToSpecialCell = new HashMap<>(); // 特殊物品

    public ItemBag() {
    }

    public ItemBag(String playerId) {
        super(playerId);
    }

    public int getMaxIdSeq() {
        return maxIdSeq;
    }

    public void setMaxIdSeq(int maxIdSeq) {
        this.maxIdSeq = maxIdSeq;
    }

    public Map<Integer, PlainCell> getTpIdToPlainCell() {
        return tpIdToPlainCell;
    }

    public void setTpIdToPlainCell(Map<Integer, PlainCell> tpIdToPlainCell) {
        this.tpIdToPlainCell = tpIdToPlainCell;
    }

    public Map<Integer, SpecialCell> getIdToSpecialCell() {
        return idToSpecialCell;
    }

    public void setIdToSpecialCell(Map<Integer, SpecialCell> idToSpecialCell) {
        this.idToSpecialCell = idToSpecialCell;
    }
}
