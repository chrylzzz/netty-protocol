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
    private Map<Integer, ProtocolProcessor> processorMap = new HashMap<>();

    //连接
    @Override
    public void start() {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(8888)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ProtocolEncoder()
                                            , new ProtocolDecoder()
                                            , new ProtocolServerProcessor()
                                    );
                        }
                    });
            ChannelFuture cf = bootstrap.bind().sync();
        } catch (InterruptedException e) {

        }
    }


    //关闭
    @Override
    public void shutdown() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public void registerProcessor(Integer code, ProtocolProcessor protocolProcessor) {
        //新增发送的数据
        processorMap.put(code, protocolProcessor);
    }

    //接收和发送：如果有客户端发送过来message数据，客户端封装的的message数据也写回客户端
    public class ProtocolServerProcessor extends SimpleChannelInboundHandler<Message> {
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
            //是否接收到数据了，接收到客户端的数据，发送
            Integer code = message.getMessageHeader().getCode();
            ProtocolProcessor processor = processorMap.get(code);
            if (processor != null) {
                Message response = processor.process(message);//客户端发送过来的message
                channelHandlerContext.writeAndFlush(response);//服务端写回去自己的message
            }
        }
    }


}
