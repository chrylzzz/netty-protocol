package com.ztglcy.nettyprotocol.service;

import com.ztglcy.nettyprotocol.handler.ProtocolDecoder;
import com.ztglcy.nettyprotocol.handler.ProtocolEncoder;
import com.ztglcy.nettyprotocol.message.Message;
import com.ztglcy.nettyprotocol.processor.ProtocolProcessor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chenyu Li
 * @description
 * @date 2018/8/23
 */
public class NettyProtocolServer implements ProtocolService {

    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Map<Integer,ProtocolProcessor> processorMap = new HashMap<>();

    @Override
    public void start(){
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(8888)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ProtocolEncoder()
                                            ,new ProtocolDecoder()
                                            ,new ProtocolServerProcessor()
                                    );
                        }
                    });
            ChannelFuture cf = bootstrap.bind().sync();
        } catch (InterruptedException e) {

        }
    }

    @Override
    public void shutdown() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public void registerProcessor(Integer code,ProtocolProcessor protocolProcessor){
        processorMap.put(code,protocolProcessor);
    }

    public class ProtocolServerProcessor extends SimpleChannelInboundHandler<Message> {

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
            Integer code = message.getMessageHeader().getCode();
            ProtocolProcessor processor = processorMap.get(code);
            if(processor != null){
                Message response = processor.process(message);
                channelHandlerContext.writeAndFlush(response);
            }
        }
    }


}
