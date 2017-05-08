package com.ectrip;

import com.ectrip.http.HttpClientUtil;
import org.junit.Test;

/**
 * Created by huangxinguang on 2017/5/8 下午6:09.
 */
public class HttpClientUtilTest {

    @Test
    public void testGet() {
        String result = HttpClientUtil.httpGetRequest("http://www.baidu.com");
    }
}
