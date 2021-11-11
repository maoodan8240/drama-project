import org.junit.Test;
import ws.common.utils.http.WsHttpClient;

public class TestVote {


    @Test
    public void isVoteMurder() {
        String s = WsHttpClient.httpSynSend_Get("http://121.36.23.124/conn_address/host_address");
        System.out.println(s);
    }
}
