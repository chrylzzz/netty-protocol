package com.ztglcy.chr.protocol.starter;

import com.sdsoon.test2.netty.protocol.handler.ProtocolDecoder;
import com.sdsoon.test2.netty.protocol.handler.ProtocolEncoder;
import com.sdsoon.test2.netty.protocol.message.DemoMessageBody;
import com.sdsoon.test2.netty.protocol.message.Message;
import com.sdsoon.test2.netty.protocol.message.MessageHeader;
import com.sdsoon.test2.netty.protocol.message.Response;
import com.sdsoon.test2.netty.protocol.util.SerializableHelper;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created By Chr on 2019/4/23.
 */
public class ClientStarter implements NettyStarter {
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    //    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Bootstrap bootstrap = new Bootstrap();
    private ConcurrentHashMap<Integer, Response> responseMap = new ConcurrentHashMap<>();


    @Override
    public void start() {

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ProtocolDecoder(),
                                new ProtocolEncoder(),
                                new ProtocolClientProcessorHandler());
                    }
                });
    }

    @Override
    public void shutdown() {

        eventLoopGroup.shutdownGracefully();
    }

    private class ProtocolClientProcessorHandler extends SimpleChannelInboundHandler<Message> {


        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

            System.err.println(SerializableHelper.
                    decode(msg.getContent(), DemoMessageBody.class).getDemo());

            Response response = responseMap.get(
                    msg.getMessageHeader().getMessageId()
            );

            if (response != null) {

                response.setMessage(msg);
            }
        }
    }


    public static void main(String args[]) {
        ClientStarter clientStarter = new ClientStarter();
        clientStarter.start();
        Message message = demoMessage();

        Message messageResponse = clientStarter.send("localhost", message);

        //??
        System.err.println(SerializableHelper.
                decode(messageResponse.getContent(), DemoMessageBody.class).getDemo()
        );

        clientStarter.shutdown();
    }

    //封装客户端发送给服务端的Message数据
    private static Message demoMessage() {

        MessageHeader messageHeader = new MessageHeader(1);
        messageHeader.setMessageId(1);
        messageHeader.setClientId(1);
        messageHeader.setServerId(1);
        //设置头信息
        Message message = Message.createMessage(messageHeader);

        //设置消息体
        DemoMessageBody responseBody = new DemoMessageBody();
        responseBody.setDemo("Hello World!");
        message.setContent(SerializableHelper.encode(responseBody));

        //返回消息
        return message;
    }

    //根据地址 发送消息
    public Message send(String address, Message message) {
        try {
            Response response = new Response();
            responseMap.put(message.getMessageHeader().getMessageId(), response);
            //连接
            Channel channel = bootstrap.connect(address, 8888).sync().channel();

            //发送message
            channel.writeAndFlush(message);

            Message responseMessage = response.getMessage();

            responseMap.remove(message.getMessageHeader().getMessageId());

            channel.close();

            return responseMessage;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
