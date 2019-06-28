package com.ztglcy.chr.protocol.prpcessor;


import com.ztglcy.chr.protocol.message.Message;

/**
 * //发送，接受 数据
 * <p>
 * Created By Chr on 2019/4/23.
 */
public interface NettyProcessor {


    Message process(Message message);

}
