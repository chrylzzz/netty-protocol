package com.ztglcy.nettyprotocol.message;


import com.ztglcy.nettyprotocol.util.SerializableHelper;

import java.nio.ByteBuffer;

/**
 * @author Chenyu Li
 * @description
 * @date 2018/8/23
 */
public class Message {

    private MessageHeader messageHeader;

    private byte[] content;

    private Message(){
    }

    public static Message createMessage(MessageHeader messageHeader){
        Message message = new Message();
        message.messageHeader = messageHeader;
        return message;
    }

    public static Message decode(ByteBuffer byteBuffer){
        int length = byteBuffer.limit();
        int headerLength = byteBuffer.getInt();

        byte[] headerData = new byte[headerLength];
        byteBuffer.get(headerData);
        MessageHeader messageHeader = SerializableHelper.decode(headerData,MessageHeader.class);

        byte[] content = new byte[length - headerLength -4];
        byteBuffer.get(content);

        Message message = Message.createMessage(messageHeader);
        message.setContent(content);
        return message;
    }



    public ByteBuffer encode(){
        int length = 4;
        byte[] bytes = SerializableHelper.encode(messageHeader);
        if(bytes != null){
            length += bytes.length;
        }
        if(content!=null){
            length += content.length;
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(length + 4);
        byteBuffer.putInt(length);
        if(bytes != null){
            byteBuffer.putInt(bytes.length);
            byteBuffer.put(bytes);
        }else{
            byteBuffer.putInt(0);
        }
        if(content!=null){
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
}
