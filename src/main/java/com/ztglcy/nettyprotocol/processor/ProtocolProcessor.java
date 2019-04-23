package com.ztglcy.nettyprotocol.processor;

import com.ztglcy.nettyprotocol.message.Message;

/**
 * 消息发送和接收
 *
 * @author Chenyu Li
 * @description
 * @date 2018/8/24
 */
public interface ProtocolProcessor {

    Message process(Message message);
}
