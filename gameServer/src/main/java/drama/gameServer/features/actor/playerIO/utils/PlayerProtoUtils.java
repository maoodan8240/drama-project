package drama.gameServer.features.actor.playerIO.utils;

import dm.relationship.topLevelPojos.player.Player;
import drama.protos.PlayerProtos;

public class PlayerProtoUtils {
    public static PlayerProtos.Sm_Player createSm_Player(Player player, PlayerProtos.Sm_Player.Action action) {
        PlayerProtos.Sm_Player.Builder b = PlayerProtos.Sm_Player.newBuilder();
        b.setAction(action);
        b.setName(player.getBase().getName());
        b.setSex(player.getBase().getSex());
        b.setIcon(player.getBase().getIcon());
        b.setBirthday(player.getBase().getBirthday());
        b.setPlace(player.getBase().getPlace());
        return b.build();
    }
}
