package com.ztglcy.nettyprotocol.demo;

import com.ztglcy.nettyprotocol.processor.DemoProcessor;
import com.ztglcy.nettyprotocol.service.NettyProtocolServer;

/**
 * @author Chenyu Li
 * @description
 * @date 2018/8/24
 */
public class ServerDemo {

    public static void main(String[] args) {
        NettyProtocolServer nettyProtocolServer = new NettyProtocolServer();
        nettyProtocolServer.registerProcessor(1,new DemoProcessor());
        nettyProtocolServer.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        nettyProtocolServer.shutdown();

    }
}
