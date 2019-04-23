package com.ztglcy.chr.protocol.starter;

import com.sdsoon.test2.netty.protocol.handler.ProtocolDecoder;
import com.sdsoon.test2.netty.protocol.handler.ProtocolEncoder;
import com.sdsoon.test2.netty.protocol.message.Message;
import com.sdsoon.test2.netty.protocol.prpcessor.DefaultNettyProcessor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By Chr on 2019/4/23.
 */
public class ServerStarter implements NettyStarter {


    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    private Map<Integer, DefaultNettyProcessor> processorMap = new HashMap<>();


    @Override
    public void start() {

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(8888)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtocolEncoder()
                                    , new ProtocolDecoder()
                                    , new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null))
                                    , new ProtocolServerProcessorHandler());
                        }
                    });
            ChannelFuture cf = bootstrap.bind().sync();//绑定
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutdown() {

        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public class ProtocolServerProcessorHandler extends SimpleChannelInboundHandler<Message> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

            Integer code = msg.getMessageHeader().getCode();
            DefaultNettyProcessor defaultNettyProcessor = processorMap.get(code);
            //接收 and  发送的消息
            if (defaultNettyProcessor != null) {
                Message process = defaultNettyProcessor.process(msg);
                ctx.writeAndFlush(process);

            }
        }
    }

    public void registerProcessor(Integer code, DefaultNettyProcessor protocolProcessor) {
        //新增发送的数据
        processorMap.put(code, protocolProcessor);
    }

    public static void main(String args[]) {
        ServerStarter serverStarter = new ServerStarter();
        //注册信息???服务端，为什么要注册
        serverStarter.registerProcessor(1, new DefaultNettyProcessor());
        //启动
        serverStarter.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //nettyProtocolServer.shutdown();
    }


}
