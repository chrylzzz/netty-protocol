package com.ztglcy.nettyprotocol.util;

import com.alibaba.fastjson.JSON;

import java.nio.charset.Charset;

/**
 * @author Chenyu Li
 * @description
 * @date 2018/8/24
 */
public class SerializableHelper {

    public static byte[] encode(Object object){
        if(object != null){
            return JSON.toJSONString(object,false).getBytes(Charset.forName("UTF-8"));
        }
        return null;
    }


    public static <T> T decode(byte[] data,Class<T> clazz){
        String string = new String(data,Charset.forName("UTF-8"));
        return JSON.parseObject(string,clazz);
    }
}
