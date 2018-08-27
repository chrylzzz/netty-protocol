package com.ztglcy.nettyprotocol.service;

import com.ztglcy.nettyprotocol.handler.ProtocolDecoder;
import com.ztglcy.nettyprotocol.handler.ProtocolEncoder;
import com.ztglcy.nettyprotocol.message.Message;
import com.ztglcy.nettyprotocol.message.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Chenyu Li
 * @description
 * @date 2018/8/24
 */
public class NettyProtocolClient implements ProtocolService {

    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private Bootstrap bootstrap = new Bootstrap();
    private ConcurrentHashMap<Integer,Response> responseMap = new ConcurrentHashMap<>();

    @Override
    public void start() {
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new ProtocolDecoder(),
                                        new ProtocolEncoder(),
                                        new ProtocolClientProcessor());
                    }
                });
    }

    @Override
    public void shutdown() {
        eventLoopGroup.shutdownGracefully();
    }

    public Message send(String address, Message message){
        try {
            Response response = new Response();
            responseMap.put(message.getMessageHeader().getMessageId(),response);
            Channel channel = bootstrap.connect(address,8888).sync().channel();

            channel.writeAndFlush(message);
            Message responseMessage = response.waitResponse();
            responseMap.remove(message.getMessageHeader().getMessageId());
            channel.close();
            return responseMessage;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class ProtocolClientProcessor extends SimpleChannelInboundHandler<Message> {

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
            Response response = responseMap.get(message.getMessageHeader().getMessageId());
            if (response != null){
                response.putResponse(message);
            }
        }
    }
}
