package drama.gameServer.features.actor.room.pojo;

import dm.relationship.base.IdAndCount;

/**
 * Created by lee on 2021/10/18
 */
public class AuctionResult {
    private int itemId;
    private int auctionId;
    private int roleId;
    private String itemName;
    private String auctionName;
    private IdAndCount auctionPrice;

    /**
     * @param itemId
     * @param roleId
     * @param price
     * @param itemName
     * @param auctionName
     */
    public AuctionResult(int itemId, int auctionId, int roleId, IdAndCount auctionPrice, String itemName, String auctionName) {
        this.itemId = itemId;
        this.roleId = roleId;
        this.auctionPrice = auctionPrice;
        this.itemName = itemName;
        this.auctionName = auctionName;
        this.auctionId = auctionId;
    }


    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public IdAndCount getAuctionPrice() {
        return auctionPrice;
    }

    public void setAuctionPrice(IdAndCount auctionPrice) {
        this.auctionPrice = auctionPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }
}
