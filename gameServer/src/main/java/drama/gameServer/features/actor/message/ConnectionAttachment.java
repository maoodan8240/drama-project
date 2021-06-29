package drama.gameServer.features.actor.message;

import akka.actor.Address;

/**
 * Created by lee on 17-7-5.
 */
public class ConnectionAttachment implements Cloneable {
    private Address address;
    private String gameId;
    private String connFlag;

    public ConnectionAttachment(Address address, String gameId, String connFlag) {
        this.address = address;
        this.gameId = gameId;
        this.connFlag = connFlag;
    }

    public Address getAddress() {
        return address;
    }

    public String getGameId() {
        return gameId;
    }

    public String getConnFlag() {
        return connFlag;
    }

    @Override
    public ConnectionAttachment clone() {
        return new ConnectionAttachment(address, gameId, connFlag);
    }
}
