package com.ztglcy.chr.protocol.handler;

import com.sdsoon.test2.netty.protocol.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteBuffer;

/**
 * Created By Chr on 2019/4/23.
 */
public class ProtocolDecoder extends LengthFieldBasedFrameDecoder {

    public ProtocolDecoder() {
        super(Integer.MAX_VALUE, 0, 4, 0, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        ByteBuf byteBuf = (ByteBuf) super.decode(ctx, in);

        if (byteBuf == null) {
            return null;
        }
        ByteBuffer byteBuffer = byteBuf.nioBuffer();
        return Message.decode(byteBuffer);
    }
}
