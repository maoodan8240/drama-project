package dm.relationship.chatServer;

import dm.relationship.base.DmCloneable;
import drama.protos.EnumsProtos.ChatTypeEnum;

import java.io.Serializable;

/**
 * Created by lee on 17-5-4.
 */
public class ChatGroupInfo implements Serializable, DmCloneable<ChatGroupInfo> {
    private static final long serialVersionUID = 3604136510577191319L;
    private ChatTypeEnum type;        // 组类型
    private String groupId;           // 组Id
    private String groupName;         // 组名称

    public ChatGroupInfo(ChatTypeEnum type, String groupId, String groupName) {
        this.type = type;
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public ChatTypeEnum getType() {
        return type;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public ChatGroupInfo clone() {
        return new ChatGroupInfo(type, groupId, groupName);
    }
}
