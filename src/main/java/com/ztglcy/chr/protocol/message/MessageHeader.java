package com.ztglcy.chr.protocol.message;

/**
 * 消息头：详细数据
 *
 * Created By Chr on 2019/4/23.
 */
public class MessageHeader {

    private int messageId;
    private int clientId;
    private int serverId;
    private int code;

    public MessageHeader(int code) {
        this.code = code;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
