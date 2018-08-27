package com.ztglcy.nettyprotocol.demo;

import com.ztglcy.nettyprotocol.message.DemoMessageBody;
import com.ztglcy.nettyprotocol.service.NettyProtocolClient;
import com.ztglcy.nettyprotocol.message.Message;
import com.ztglcy.nettyprotocol.message.MessageHeader;
import com.ztglcy.nettyprotocol.util.SerializableHelper;

/**
 * @author Chenyu Li
 * @description
 * @date 2018/8/24
 */
public class ClientDemo {
    public static void main(String[] args) {
        NettyProtocolClient client = new NettyProtocolClient();
        client.start();
        Message message = demoMessage();
        Message messageResponse = client.send("localhost",message);
        System.out.println(SerializableHelper.decode(messageResponse.getContent(),DemoMessageBody.class).getDemo());
        client.shutdown();
    }

    private static Message demoMessage(){
        MessageHeader messageHeader = new MessageHeader(1);
        messageHeader.setMessageId(1);
        messageHeader.setClientId(1);
        messageHeader.setServerId(1);
        Message message =Message.createMessage(messageHeader);
        DemoMessageBody responseBody = new DemoMessageBody();
        responseBody.setDemo("Hello World!");
        message.setContent(SerializableHelper.encode(responseBody));
        return message;
    }
}
