package com.ectrip.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

/**
 * Created by huangxinguang on 2017/5/9 上午10:17.
 */
public class FastJsonUtil {
    private static final SerializeConfig config;

    static {
        config = new SerializeConfig();
        config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
        config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
    }

    private static final SerializerFeature[] features = {SerializerFeature.WriteMapNullValue, // 输出空置字段
            SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
    };

    /**
     * 转化为json字符串
     * @param object
     * @return
     */
    public static String toJSONString(Object object) {
        return JSON.toJSONString(object, config, features);
    }

    /**
     * 转化为json字符串
     * @param object
     * @return
     */
    public static String toJSONNoFeatures(Object object) {
        return JSON.toJSONString(object, config);
    }


    /**
     * json字符串转化为对象
     * @param jsonStr
     * @return
     */
    public static Object toBean(String jsonStr) {
        return JSON.parse(jsonStr);
    }

    /**
     * json字符串转化为指定的类
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toBean(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, clazz);
    }

    /**
     * json字符串转化为对象数组
     * @param jsonStr
     * @param <T>
     * @return
     */
    public static <T> Object[] toArray(String jsonStr) {
        return toArray(jsonStr, null);
    }

    /**
     * 转化为特定类的数组
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Object[] toArray(String jsonStr, Class<T> clazz) {
        return JSON.parseArray(jsonStr, clazz).toArray();
    }

    /**
     * 转化为list
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(String jsonStr, Class<T> clazz) {
        return JSON.parseArray(jsonStr, clazz);
    }


    /**
     * 将string转化为序列化的json字符串
     * @param text
     * @return
     */
    public static Object textToSerializeJson(String text) {
        Object objectJson  = JSON.parse(text);
        return objectJson;
    }

    /**
     * json字符串转化为map
     * @param jsonStr
     * @return
     */
    public static Map jsonStringToMap(String jsonStr) {
        Map map = JSONObject.parseObject(jsonStr);
        return map;
    }

    /**
     * 将map转化为string
     * @param map
     * @return
     */
    public static String mapToJsonString(Map map) {
        String jsonStr = JSONObject.toJSONString(map);
        return jsonStr;
    }
}
