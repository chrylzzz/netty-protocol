package com.ztglcy.chr.protocol.message;

import com.sdsoon.test2.netty.protocol.util.SerializableHelper;

import java.nio.ByteBuffer;

/**
 * 消息数据
 *
 *
 * Created By Chr on 2019/4/23.
 */
public class Message {

    //消息体
    private byte[] content;
    //消息头
    private MessageHeader messageHeader;

    public Message() {
    }

    public Message(byte[] content, MessageHeader messageHeader) {
        this.content = content;
        this.messageHeader = messageHeader;
    }

    //创建信息
    public static Message createMessage(MessageHeader messageHeader) {
        Message message = new Message();
        message.messageHeader = messageHeader;
        return message;
    }


    //消息数据   解码--》 io基于byteBuffer，接收方根据byteBuffer解码到正常的javaBean
    public static Message decode(ByteBuffer byteBuffer) {
        int length = byteBuffer.limit();
        int headerLength = byteBuffer.getInt();

        byte[] headerData = new byte[headerLength];
        byteBuffer.get(headerData);
        MessageHeader messageHeader = SerializableHelper.decode(headerData, MessageHeader.class);

        byte[] content = new byte[length - headerLength - 4];
        byteBuffer.get(content);

        Message message = Message.createMessage(messageHeader);
        message.setContent(content);
        return message;
    }


    //编码：发送发需要编码到byteBuffer格式发送
    public ByteBuffer encode() {
        int length = 4;
        byte[] bytes = SerializableHelper.encode(messageHeader);
        if (bytes != null) {
            length += bytes.length;
        }
        if (content != null) {
            length += content.length;
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(length + 4);
        byteBuffer.putInt(length);
        if (bytes != null) {
            byteBuffer.putInt(bytes.length);
            byteBuffer.put(bytes);
        } else {
            byteBuffer.putInt(0);
        }
        if (content != null) {
            byteBuffer.put(content);
        }
        byteBuffer.flip();

        return byteBuffer;
    }
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }
}
