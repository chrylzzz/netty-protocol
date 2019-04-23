package com.ztglcy.nettyprotocol.demo;

import com.ztglcy.nettyprotocol.message.DemoMessageBody;
import com.ztglcy.nettyprotocol.message.Message;
import com.ztglcy.nettyprotocol.message.MessageHeader;
import com.ztglcy.nettyprotocol.service.NettyProtocolClient;
import com.ztglcy.nettyprotocol.util.SerializableHelper;

/**
 * @author Chenyu Li
 * @description
 * @date 2018/8/24
 */
public class ClientDemo {
    public static void main(String[] args) {
        NettyProtocolClient client = new NettyProtocolClient();
        client.start();//连接完毕
        //客户端发送给服务端数据
        Message message = demoMessage();//hello world
        Message messageResponse = client.send("localhost", message);

        //接收到服务端发来的message数据
        System.err.println(SerializableHelper.
                decode(messageResponse.getContent(), DemoMessageBody.class).getDemo()
        );

        client.shutdown();
    }

    //封装客户端发送给服务端的Message数据
    private static Message demoMessage() {
        MessageHeader messageHeader = new MessageHeader(1);
        messageHeader.setMessageId(1);
        messageHeader.setClientId(1);
        messageHeader.setServerId(1);
        Message message = Message.createMessage(messageHeader);
        DemoMessageBody responseBody = new DemoMessageBody();
        responseBody.setDemo("Hello World!");
        message.setContent(SerializableHelper.encode(responseBody));
        return message;
    }
}
