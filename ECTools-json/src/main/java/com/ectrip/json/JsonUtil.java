package com.ectrip.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.Writer;

/**
 * Created by sunshine on 16/7/25.
 */
public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            logger.warn("object转换json异常", e);
        }
        return null;
    }

    public static <T> T toObject(String json, Class<T> valueType) {
        Assert.hasText(json);
        Assert.notNull(valueType);
        try {
            return (T) mapper.readValue(json, valueType);
        } catch (Exception e) {
            logger.warn("JSON转换object异常", e);
        }
        return null;
    }

    public static <T> T toObject(String json, TypeReference<?> typeReference) {
        Assert.hasText(json);
        Assert.notNull(typeReference);
        try {
            return (T) mapper.readValue(json, typeReference);
        } catch (Exception e) {
            logger.warn("Json转换Object异常", e);
        }
        return null;
    }

    public static <T> T toObject(String json, JavaType javaType) {
        Assert.hasText(json);
        Assert.notNull(javaType);
        try {
            return (T) mapper.readValue(json, javaType);
        } catch (Exception e) {
            logger.warn("json转换OBJECT异常:", e);
        }
        return null;
    }

    public static void writeValue(Writer writer, Object value) {
        try {
            mapper.writeValue(writer, value);
        } catch (Exception e) {
            logger.warn("object转json异常", e);
        }
    }
}
