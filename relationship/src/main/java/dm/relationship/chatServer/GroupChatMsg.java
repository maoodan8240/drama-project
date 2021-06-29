package dm.relationship.chatServer;

import drama.protos.EnumsProtos.ChatTypeEnum;

/**
 * Created by lee on 17-5-5.
 */
public class GroupChatMsg extends ChatMsg {
    private static final long serialVersionUID = 381717064083491199L;
    public static final String TO_ALL_PLAYER_GROUP_ID = "TAPG";
    private String groupId = TO_ALL_PLAYER_GROUP_ID;                 // 组的识别码,默认为发送给所有人的GroupId
    private String senderPlayerId;                                   // 发送者
    private String content;                                          // 发送的内容
    private boolean useHorn;                                         // 是否使用了喇叭
    private SystemChatMsg systemChatMsg;                             // 系统消息

    /**
     * 队伍-系统发送的消息
     *
     * @param type
     * @param systemChatMsg
     */
    public GroupChatMsg(ChatTypeEnum type, SystemChatMsg systemChatMsg) {
        super(type);
        this.systemChatMsg = systemChatMsg;
    }

    /**
     * 队伍-系统发送的消息
     *
     * @param type
     * @param groupId
     * @param systemChatMsg
     */
    public GroupChatMsg(ChatTypeEnum type, String groupId, SystemChatMsg systemChatMsg) {
        super(type);
        this.groupId = groupId;
        this.systemChatMsg = systemChatMsg;
    }

    /**
     * 队伍-个人发送的消息
     *
     * @param type
     * @param groupId
     * @param senderPlayerId
     * @param content
     * @param useHorn
     */
    public GroupChatMsg(ChatTypeEnum type, String groupId, String senderPlayerId, String content, boolean useHorn) {
        super(type);
        this.groupId = groupId;
        this.senderPlayerId = senderPlayerId;
        this.content = content;
        this.useHorn = useHorn;
    }

    /**
     * for clone
     */
    public GroupChatMsg(long chatId, ChatTypeEnum type, long time, String groupId, String senderPlayerId, String content, boolean useHorn, SystemChatMsg systemChatMsg) {
        super(chatId, type, time);
        this.groupId = groupId;
        this.senderPlayerId = senderPlayerId;
        this.content = content;
        this.useHorn = useHorn;
        this.systemChatMsg = systemChatMsg;
    }

    public String getGroupId() {
        return groupId;
    }

    public boolean isUseHorn() {
        return useHorn;
    }

    public String getSenderPlayerId() {
        return senderPlayerId;
    }

    public String getContent() {
        return content;
    }

    public SystemChatMsg getSystemChatMsg() {
        return systemChatMsg;
    }

    @Override
    public GroupChatMsg clone() {
        return new GroupChatMsg(chatId, type, time, groupId, senderPlayerId,
                content, useHorn, systemChatMsg);
    }
}
