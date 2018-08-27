package com.ztglcy.nettyprotocol.handler;

import com.ztglcy.nettyprotocol.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.ByteBuffer;

/**
 * @author Chenyu Li
 * @description
 * @date 2018/8/23
 */
public class ProtocolEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {

        ByteBuffer byteBuffer = message.encode();
        byteBuf.writeBytes(byteBuffer);

    }
}
