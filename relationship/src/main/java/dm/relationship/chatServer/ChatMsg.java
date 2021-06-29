package dm.relationship.chatServer;

import drama.protos.EnumsProtos.ChatTypeEnum;

import java.io.Serializable;

/**
 * Created by lee on 17-5-5.
 */
public abstract class ChatMsg implements Serializable {
    private static final long serialVersionUID = 381717064083491199L;
    protected long chatId;
    protected ChatTypeEnum type;

    protected long time = System.currentTimeMillis();

    public ChatMsg(ChatTypeEnum type) {
        this.type = type;
    }

    /**
     * for clone
     */
    public ChatMsg(long chatId, ChatTypeEnum type, long time) {
        this.chatId = chatId;
        this.type = type;
        this.time = time;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getChatId() {
        return chatId;
    }

    public ChatTypeEnum getType() {
        return type;
    }

    public long getTime() {
        return time;
    }

    public abstract ChatMsg clone();
}
