package drama.gameServer.features.extp.itemBag.pojo;

import dm.relationship.noDbPojo.RoomPlayerNoDbPojo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lee on 2021/9/30
 */
public class ItemBag extends RoomPlayerNoDbPojo {
    private String playerId;
    private int maxIdSeq; // itemId 最大的顺序号
    private Map<Integer, PlainCell> tpIdToPlainCell = new HashMap<>(); // 普通物品
    private Map<Integer, SpecialCell> idToSpecialCell = new HashMap<>(); // 特殊物品

    public ItemBag() {
    }

    public ItemBag(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
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
