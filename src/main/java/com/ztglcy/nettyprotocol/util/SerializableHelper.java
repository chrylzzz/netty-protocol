package com.ztglcy.nettyprotocol.util;

import com.alibaba.fastjson.JSON;

import java.nio.charset.Charset;

/**
 * 解码和编码
 *
 * @author Chenyu Li
 * @description
 * @date 2018/8/24
 */
public class SerializableHelper {

    //编码
    public static byte[] encode(Object object) {
        if (object != null) {
            //第二个参数，是否格式化。
            /**
             * 格式化:
             * {
             *    {
             *        ...
             *    } ,
             *    {
             *        ...
             *    }
             * }
             * 非格式化：{{...},{...}}
             */
            return JSON.toJSONString(object, false).getBytes(Charset.forName("UTF-8"));
        }
        return null;
    }


    //解码
    public static <T> T decode(byte[] data, Class<T> clazz) {
        String string = new String(data, Charset.forName("UTF-8"));
        return JSON.parseObject(string, clazz);
    }
}
