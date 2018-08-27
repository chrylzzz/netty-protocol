package com.ztglcy.nettyprotocol.processor;

import com.ztglcy.nettyprotocol.message.DemoMessageBody;
import com.ztglcy.nettyprotocol.message.Message;
import com.ztglcy.nettyprotocol.message.MessageHeader;
import com.ztglcy.nettyprotocol.util.SerializableHelper;

/**
 * @author Chenyu Li
 * @description
 * @date 2018/8/24
 */
public class DemoProcessor implements ProtocolProcessor{

    @Override
    public Message process(Message message) {
        byte[] bodyDate = message.getContent();
        DemoMessageBody messageBody = SerializableHelper.decode(bodyDate,DemoMessageBody.class);
        System.out.println(messageBody.getDemo());

        MessageHeader messageHeader = new MessageHeader(1);
        messageHeader.setMessageId(message.getMessageHeader().getMessageId());
        DemoMessageBody responseBody = new DemoMessageBody();
        responseBody.setDemo("I received!");

        Message response = Message.createMessage(messageHeader);
        response.setContent(SerializableHelper.encode(responseBody));
        return response;

    }
}
