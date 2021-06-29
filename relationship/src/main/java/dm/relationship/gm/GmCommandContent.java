package dm.relationship.gm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 17-4-10.
 */
public class GmCommandContent implements Serializable {
    private static final long serialVersionUID = 3646541326622306002L;
    private List<Integer> simpleIdLis = new ArrayList<>();
    private String command;

    public GmCommandContent() {
    }

    public List<Integer> getSimpleIdLis() {
        return simpleIdLis;
    }

    public void setSimpleIdLis(List<Integer> simpleIdLis) {
        this.simpleIdLis = simpleIdLis;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
