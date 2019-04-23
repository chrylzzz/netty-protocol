package com.ztglcy.nettyprotocol.processor;

import com.ztglcy.nettyprotocol.message.DemoMessageBody;
import com.ztglcy.nettyprotocol.message.Message;
import com.ztglcy.nettyprotocol.message.MessageHeader;
import com.ztglcy.nettyprotocol.util.SerializableHelper;

/**
 * 封装 服务端 发送给客户端的Message数据
 *
 * @author Chenyu Li
 * @description
 * @date 2018/8/24
 */
public class DemoProcessor implements ProtocolProcessor {

    @Override
    public Message process(Message message) {
        /**
         * 接收
         */
        byte[] bodyDate = message.getContent();
        //接收到客户端的数据进行解密
        DemoMessageBody messageBody = SerializableHelper.decode(bodyDate, DemoMessageBody.class);
        //客户端的message信息
        System.err.println(messageBody.getDemo());

        /**
         * 发送
         */
        MessageHeader messageHeader = new MessageHeader(1);
        messageHeader.setMessageId(message.getMessageHeader().getMessageId());
        DemoMessageBody responseBody = new DemoMessageBody();
        //服务端给客户端的message
        responseBody.setDemo("I received!");//received
        Message response = Message.createMessage(messageHeader);
        //发送出去的数据要进行加密
        response.setContent(SerializableHelper.encode(responseBody));
        return response;

    }
}
