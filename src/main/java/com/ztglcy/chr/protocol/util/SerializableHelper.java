package com.ztglcy.chr.protocol.util;

import com.alibaba.fastjson.JSON;
import io.netty.util.CharsetUtil;

/**
 * Created By Chr on 2019/4/23.
 */
public class SerializableHelper {

    public static byte[] encode(Object o) {

        if (o != null) {
            return JSON.toJSONString(o, false).getBytes(CharsetUtil.UTF_8);

        }
        return null;
    }


    public static <T> T decode(byte[] data, Class<T> clazz) {
        String s = new String(data, CharsetUtil.UTF_8);
        return JSON.parseObject(s, clazz);
    }
}
