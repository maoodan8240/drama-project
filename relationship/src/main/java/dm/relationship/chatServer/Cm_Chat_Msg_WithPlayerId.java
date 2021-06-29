package dm.relationship.chatServer;

import com.google.protobuf.Message;

import java.io.Serializable;

/**
 * Created by lee on 17-5-5.
 */
public class Cm_Chat_Msg_WithPlayerId implements Serializable {
    private static final long serialVersionUID = -693636042602891918L;
    private Message message;
    private String playerId;

    public Cm_Chat_Msg_WithPlayerId(Message message, String playerId) {
        this.message = message;
        this.playerId = playerId;
    }

    public Message getMessage() {
        return message;
    }

    public String getPlayerId() {
        return playerId;
    }
}
