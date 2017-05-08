package com.ectrip.http.config;

/**
 * Created by huangxinguang on 2017/3/27 ����9:10.
 * </p>
 * Desc:
 */
public class HttpClientConfig {
    /**
     * 编码
     */
    public static String ENCODING = "UTF-8";
    /**
     * 连接池最大链接数
     */
    public static final int MAX_TOTAL = 60;
    /**
     * 路由数
     */
    public static final int MAX_PER_ROUTE = 5;
    /**
     * timeOut
     */
    public static final int CONNECT_TIMEOUT = 20 * 1000;
    /**
     * socket timeout
     */
    public static final int SOCKET_TIMEOUT = 20 * 1000;
    /**
     * 重连次数
     */
    public static final int RETRY_NUM = 8;
}
