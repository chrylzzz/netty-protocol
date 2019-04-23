package com.ztglcy.chr.protocol.message;

import java.util.concurrent.CountDownLatch;

/**
 * Created By Chr on 2019/4/23.
 */
public class Response {

    private Message message;
   private CountDownLatch countDownLatch = new CountDownLatch(1);
    public Message getMessage() throws InterruptedException {
        countDownLatch.await();
        return message;
    }

    public void setMessage(Message message) {

        this.message = message;
        countDownLatch.countDown();

    }
}
