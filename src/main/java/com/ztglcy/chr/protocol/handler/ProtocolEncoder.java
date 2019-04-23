package com.ztglcy.chr.protocol.handler;

import com.sdsoon.test2.netty.protocol.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.ByteBuffer;

/**
 * Created By Chr on 2019/4/23.
 */
public class ProtocolEncoder extends MessageToByteEncoder<Message> {
//    @Override
//    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
//
//    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        ByteBuffer byteBuffer = msg.encode();//msg变为buffer

        out.writeBytes(byteBuffer);
    }
}
