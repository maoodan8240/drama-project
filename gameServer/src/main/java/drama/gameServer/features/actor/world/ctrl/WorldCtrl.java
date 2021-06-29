package drama.gameServer.features.actor.world.ctrl;

import akka.actor.ActorRef;
import dm.relationship.daos.player.PlayerDao;
import drama.gameServer.features.actor.world.pojo.World;
import ws.common.utils.mc.controler.Controler;

public interface WorldCtrl extends Controler<World> {

    ActorRef getPlayerActorRef(String playerId);

    void remove(String playerId);


    boolean playerActorCanUse(String playerId);

    PlayerDao getPlayerDao();

    void putRoomActorRef(String roomId, ActorRef roomActorRef);

    ActorRef getRoomActorRef(String roomId);

    void killRoomActor(String roomId);

    void addPlayerIdAndActor(String playerId, String mobileNum, ActorRef playerActorRef);

    boolean roomActorCanUse(String roomId);

}
