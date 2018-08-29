package com.ztglcy.nettyprotocol.message;

/**
 * @author Chenyu Li
 * @description
 * @date 2018/8/23
 */
public class MessageHeader{

    private int messageId;
    private int clientId;
    private int serverId;
    private int code;

    private MessageHeader(){}

    public MessageHeader(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
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

    public void setCode(int code) {
        this.code = code;
    }

}
