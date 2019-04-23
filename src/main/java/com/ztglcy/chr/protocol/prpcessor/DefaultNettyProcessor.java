package com.ztglcy.chr.protocol.prpcessor;

import com.sdsoon.test2.netty.protocol.message.DemoMessageBody;
import com.sdsoon.test2.netty.protocol.message.Message;
import com.sdsoon.test2.netty.protocol.message.MessageHeader;
import com.sdsoon.test2.netty.protocol.util.SerializableHelper;

/**
 * Created By Chr on 2019/4/23.
 */
public class DefaultNettyProcessor implements NettyProcessor {
    @Override
    public Message process(Message message) {

        //接受
        byte[] content = message.getContent();

        DemoMessageBody decode = SerializableHelper.decode(content, DemoMessageBody.class);

        //服务端接收
        System.err.println(decode.getDemo());


        //发送
        MessageHeader messageHeader = new MessageHeader(1);
        messageHeader.setMessageId(message.getMessageHeader().getMessageId());
        DemoMessageBody responseMsgBody = new DemoMessageBody();

        responseMsgBody.setDemo("I received");

        Message resp = Message.createMessage(messageHeader);

        byte[] encode = SerializableHelper.encode(responseMsgBody);//为什么不是responseMsgBody.getDemo(); 这是消息头+消息体

        resp.setContent(encode);

        return resp;
    }
}
