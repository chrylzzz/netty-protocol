package com.ztglcy.nettyprotocol.message;

import java.util.concurrent.CountDownLatch;

/**
 * @author Chenyu Li
 * @description
 * @date 2018/8/24
 */
public class Response {
    private Message message;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void putResponse(Message message){
        this.message = message;
        countDownLatch.countDown();
    }

    public Message waitResponse() throws InterruptedException{
        countDownLatch.await();
        return message;
    }

}
