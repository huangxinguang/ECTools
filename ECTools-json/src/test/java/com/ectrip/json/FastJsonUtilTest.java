package com.ectrip.json;

import org.junit.Test;

import java.util.Map;

/**
 * Created by huangxinguang on 2017/5/9 上午10:27.
 */
public class FastJsonUtilTest {

    @Test
    public void testFastJson() {
        String json = "{'id':1,'message':'success'}";
        Map map = FastJsonUtil.jsonStringToMap(json);
    }
}
