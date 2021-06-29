package dm.relationship.chatServer;

import drama.protos.EnumsProtos.ChatTypeEnum;

/**
 * Created by lee on 17-5-5.
 */
public class PrivateChatMsg extends ChatMsg {
    private static final long serialVersionUID = 381717064083491199L;
    private long relationId;
    private String senderPlayerId;
    private String revPlayerId;
    private String content;
    private SystemChatMsg systemChatMsg;


    /**
     * 私聊-个人发送的消息
     *
     * @param relationId
     * @param senderPlayerId
     * @param revPlayerId
     * @param content
     */
    public PrivateChatMsg(long relationId, String senderPlayerId, String revPlayerId, String content) {
        super(ChatTypeEnum.CHAT_PRIVATE);
        this.relationId = relationId;
        this.senderPlayerId = senderPlayerId;
        this.revPlayerId = revPlayerId;
        this.content = content;
    }

    /**
     * 私聊-系统发送的消息
     *
     * @param relationId
     * @param senderPlayerId
     * @param revPlayerId
     * @param systemChatMsg
     */
    public PrivateChatMsg(long relationId, String senderPlayerId, String revPlayerId, SystemChatMsg systemChatMsg) {
        super(ChatTypeEnum.CHAT_PRIVATE);
        this.relationId = relationId;
        this.senderPlayerId = senderPlayerId;
        this.revPlayerId = revPlayerId;
        this.systemChatMsg = systemChatMsg;
    }

    /**
     * for clone
     */
    public PrivateChatMsg(long chatId, ChatTypeEnum type, long time, long relationId, String senderPlayerId, String revPlayerId, String content) {
        super(chatId, type, time);
        this.relationId = relationId;
        this.senderPlayerId = senderPlayerId;
        this.revPlayerId = revPlayerId;
        this.content = content;
    }


    public long getRelationId() {
        return relationId;
    }

    public String getSenderPlayerId() {
        return senderPlayerId;
    }

    public String getRevPlayerId() {
        return revPlayerId;
    }

    public String getContent() {
        return content;
    }

    public SystemChatMsg getSystemChatMsg() {
        return systemChatMsg;
    }

    public void setRelationId(long relationId) {
        this.relationId = relationId;
    }

    @Override
    public PrivateChatMsg clone() {
        return new PrivateChatMsg(chatId, type, time, relationId,
                senderPlayerId, revPlayerId, content);
    }
}
