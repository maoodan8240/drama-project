package drama.gameServer.features.actor.room.ctrl;

import akka.actor.ActorRef;
import dm.relationship.base.IdMaptoCount;
import dm.relationship.base.MagicNumbers;
import dm.relationship.table.tableRows.Table_Acter_Row;
import dm.relationship.topLevelPojos.simplePlayer.SimplePlayer;
import drama.gameServer.features.actor.room.mc.extension.RoomPlayerExtension;
import drama.gameServer.features.actor.room.mc.extensionIniter.ExtIniterUtils;
import drama.gameServer.features.actor.room.pojo.AuctionResult;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.gameServer.features.extp.itemBag.ItemBagExtp;
import drama.gameServer.features.extp.itemBag.msg.Pr_InitPropMsg;
import drama.gameServer.features.extp.utils.LoadAllRoomPlayerExtensions;
import drama.protos.MessageHandlerProtos.Response;
import org.slf4j.LoggerFactory;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.network.server.interfaces.Connection;
import ws.common.utils.mc.controler.AbstractControler;
import ws.common.utils.mc.extension.Extension;
import ws.common.utils.message.interfaces.PrivateMsg;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class _RoomPlayerCtrl extends AbstractControler<RoomPlayer> implements RoomPlayerCtrl {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(_RoomPlayerCtrl.class);
    private TreeMap<String, RoomPlayerExtension<?>> nameMapToExtension = new TreeMap<>();
    private ActorRef roomActorRef;


    @Override
    public void sendPrivateMsg(PrivateMsg privateMsg) {

    }

    public ActorRef getRoomActorRef() {
        return this.roomActorRef;
    }

    public void setRoomActorRef(ActorRef roomActorRef) {
        this.roomActorRef = roomActorRef;
    }

    @Override
    public void send(Response build) {
        getTarget().getConnection().send(new MessageSendHolder(build, build.getSmMsgAction(), new ArrayList<>()));
    }

    @Override
    public void addAuctionResult(int runDown, AuctionResult auctionResult) {
        if (target.getNumToAuctionResults().get(runDown) == null) {
            target.getNumToAuctionResults().put(runDown, new ArrayList<>());
        }
        target.getNumToAuctionResults().get(runDown).add(auctionResult);
    }

    @Override
    public List<AuctionResult> getAuctionResult(int runDown) {
        if (target.getNumToAuctionResults().get(runDown) == null) {
            target.getNumToAuctionResults().put(runDown, new ArrayList<>());
        }
        return target.getNumToAuctionResults().get(runDown);
    }

    @Override
    public RoomPlayer createRoomPlayer(SimplePlayer simplePlayer, String roomId, int dramaId, Connection connection) {
        RoomPlayer roomPlayer = new RoomPlayer(simplePlayer, roomId, dramaId, connection);
        ExtIniterUtils.initAllExtensions(roomPlayer);
        LoadAllRoomPlayerExtensions.loadAll(this);
        setTarget(roomPlayer);
        for (RoomPlayerExtension<?> roomPlayerExtension : getAllExtensions().values()) {
            try {
                roomPlayerExtension.init();
            } catch (Throwable t) {
                LOGGER.error("RoomPlayerExtension init Error ,ext={}", roomPlayerExtension, t);
                continue;
            }
        }
        return roomPlayer;
    }

    public boolean containsClueId(int clueId) {
        return target.getClueIds().contains(clueId);
    }


    public void addClueId(int clueId) {
        target.getClueIds().add(clueId);
    }

    public void reduceSrchTimes() {
        target.setSrchTimes(target.getSrchTimes() - MagicNumbers.DEFAULT_ONE > MagicNumbers.DEFAULT_ZERO ? target.getSrchTimes() - MagicNumbers.DEFAULT_ONE : MagicNumbers.DEFAULT_ZERO);
    }

    public void reduceVoteSrchTimes() {
        target.setVoteSrchTimes(target.getVoteSrchTimes() - MagicNumbers.DEFAULT_ONE > MagicNumbers.DEFAULT_ZERO ? target.getVoteSrchTimes() - MagicNumbers.DEFAULT_ONE : MagicNumbers.DEFAULT_ZERO);
    }

    @Override
    public void setRoleId(int roleIdx) {
        target.setRoleId(roleIdx);
    }

    @Override
    public int getRoleId() {
        return target.getRoleId();
    }

    @Override
    public boolean hasRole() {
        return getRoleId() != MagicNumbers.DEFAULT_ZERO;
    }

    @Override
    public int getSrchTimes() {
        return target.getSrchTimes();
    }

    @Override
    public void setSrchTimes(int times) {
        target.setSrchTimes(times);
    }

    @Override
    public String getPlayerId() {
        return target.getPlayerId();
    }

    @Override
    public boolean isReady() {
        return target.isReady();
    }

    @Override
    public void setReady(boolean b) {
        target.setReady(b);
    }

    @Override
    public int getIsDub() {
        return target.getIsDub();
    }

    @Override
    public void setDub(int isDub) {
        target.setIsDub(isDub);
    }

    @Override
    public List<Integer> getClueIds() {
        return target.getClueIds();
    }

    @Override
    public void addSoloAnswer(int soloNum, int soloDramaId) {
        target.getSoloAnswer().put(soloNum, soloDramaId);
    }


    @Override
    public boolean hasSelectDraft() {
        return target.isSelectDraft();
    }

    @Override
    public void setSelectDraft(boolean isSelect) {
        target.setSelectDraft(isSelect);
    }

    @Override
    public void setVoteSrchTimes(int voteSrchTimes) {
        target.setVoteSrchTimes(voteSrchTimes);
    }

    @Override
    public int getVoteSrchTimes() {
        return target.getVoteSrchTimes();
    }

    @Override
    public void setVoteMurder(boolean voteMurder) {
        target.setVoteMurder(voteMurder);
    }

    @Override
    public void setSubVoteMurder(boolean subVoteMurder) {
        target.setSubVoteMurder(subVoteMurder);
    }

    @Override
    public boolean isVoteMurder() {
        return target.isVoteMurder();
    }

    @Override
    public boolean isSubVoteMurder() {
        return target.isSubVoteMurder();
    }

    @Override
    public boolean hasSelectedSubRole(int subNum) {
        return target.getSubNumToSubRoleId().get(subNum) != null;
    }

    @Override
    public int getSubRoleId(int subNum) {
        return target.getSubNumToSubRoleId().get(subNum) != null ? target.getSubNumToSubRoleId().get(subNum) : 0;
    }

    @Override
    public void setSelectSubRole(int subRoleId, int subNum) {
        target.getSubNumToSubRoleId().put(subNum, subRoleId);
    }

    @Override
    public void addExtension(RoomPlayerExtension<?> extension) {
        String extensionName = parseExtensionNameFrom(extension.getClass());
        nameMapToExtension.put(extensionName, extension);
    }

    @Override
    public <T extends RoomPlayerExtension<?>> T getExtension(Class<T> type) {
        String extensionName = parseExtensionNameFrom(type);
        return (T) nameMapToExtension.get(extensionName);
    }

    @Override
    public TreeMap<String, RoomPlayerExtension<?>> getAllExtensions() {
        return nameMapToExtension;
    }

    @Override
    public void initProp(int dramaId) {
        if (Table_Acter_Row.hasProp(dramaId, getRoleId())) {
            IdMaptoCount prop = Table_Acter_Row.getProp(dramaId, getRoleId());
            getExtension(ItemBagExtp.class).onRecvPrivateMsg(new Pr_InitPropMsg(prop));
        }
    }

    @Override
    public String getRoleName() {
        return target.getRoleName();
    }

    @Override
    public void setRoleName(String roleName) {
        target.setRoleName(roleName);
    }

    @SuppressWarnings("rawtypes")
    private static String parseExtensionNameFrom(Class<? extends Extension> extensionType) {
        return extensionType.getName().replaceAll("\\.", "_");
    }
}
