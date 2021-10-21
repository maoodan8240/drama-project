package drama.gameServer.features.actor.room.pojo;

import dm.relationship.base.IdAndCount;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lee on 2021/10/15
 */
public class Auction {
    /**
     * 拍卖物品
     */
    private int itemId;
    /**
     * 拍卖ID
     */
    private int auctionId;
    /**
     * 角色IdTo出价
     */
    Map<Integer, IdAndCount> roleIdAndAuctionPice = new HashMap<>();
    private String auctionName;

    public Auction(int itemId, int auctionId, String auctioName) {
        this.itemId = itemId;
        this.auctionId = auctionId;
        this.auctionName = auctioName;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public void setRoleIdAndAuctionPice(Map<Integer, IdAndCount> roleIdAndAuctionPice) {
        this.roleIdAndAuctionPice = roleIdAndAuctionPice;
    }

    public Map<Integer, IdAndCount> getRoleIdAndAuctionPice() {
        return roleIdAndAuctionPice;
    }
}
