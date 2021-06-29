package dm.relationship.chatServer;

import dm.relationship.utils.CloneUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lee on 17-5-5.
 */
public class SystemChatMsg implements Serializable {
    private static final long serialVersionUID = 381717064083491199L;
    private int chatTpId;
    private List<String> args = new ArrayList<>();

    /**
     * 系统发送的消息
     *
     * @param chatTpId
     * @param args
     */
    public SystemChatMsg(int chatTpId, String... args) {
        this.chatTpId = chatTpId;
        if (args != null && args.length > 0) {
            this.args = new ArrayList<>(Arrays.asList(args));
        }
    }

    public SystemChatMsg(int chatTpId, List<String> args) {
        this.chatTpId = chatTpId;
        this.args = args;
    }

    public int getChatTpId() {
        return chatTpId;
    }

    public List<String> getArgs() {
        return args;
    }

    @Override
    public SystemChatMsg clone() {
        return new SystemChatMsg(chatTpId, CloneUtils.cloneStringList(args));
    }
}
