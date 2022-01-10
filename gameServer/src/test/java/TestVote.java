import dm.relationship.base.IdAndCount;
import drama.gameServer.features.actor.room.pojo.Auction;
import org.junit.Test;

public class TestVote {


    @Test
    public void isVoteMurder() {
        Auction auction = new Auction(23, 1, "11");
        auction.getRoleIdAndAuctionPice().put(1, new IdAndCount(1, 100));
        System.out.println(auction);
    }
}
