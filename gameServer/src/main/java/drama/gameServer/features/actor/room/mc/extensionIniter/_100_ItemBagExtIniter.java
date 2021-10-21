package drama.gameServer.features.actor.room.mc.extensionIniter;

import drama.gameServer.features.actor.room.mc.extensionIniter.base.ExtCommonData;
import drama.gameServer.features.actor.room.mc.extensionIniter.base.ExtensionIniter;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.gameServer.features.extp.itemBag.pojo.ItemBag;

/**
 * Created by lee on 2021/10/8
 */
public class _100_ItemBagExtIniter implements ExtensionIniter {
    @Override
    public void init(ExtCommonData commonData) throws Exception {
        RoomPlayer roomPlayer = commonData.getRoomPlayer();
        ItemBag itemBag = new ItemBag(roomPlayer.getPlayerId());
        itemBag.setRoomId(roomPlayer.getRoomId());
        itemBag.setDramaId(roomPlayer.getDramaId());
        commonData.addNoDbPojo(itemBag);
    }
}
