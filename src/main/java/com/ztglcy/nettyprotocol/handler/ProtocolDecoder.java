package com.ztglcy.nettyprotocol.handler;

import com.ztglcy.nettyprotocol.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteBuffer;

/**
 * @author Chenyu Li
 * @description
 * @date 2018/8/23
 */
public class ProtocolDecoder extends LengthFieldBasedFrameDecoder {

    public ProtocolDecoder() {
        super(16777216, 0, 4,0,4);
    }

    @Override
    public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf byteBuf = (ByteBuf) super.decode(ctx, in);
        if(byteBuf == null){
            return null;
        }
        ByteBuffer byteBuffer = byteBuf.nioBuffer();
        return Message.decode(byteBuffer);
    }

}
