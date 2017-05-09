package com.ectrip.http;

import com.ectrip.http.config.HttpClientConfig;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by huangxinguang on 2017/3/13
 * </p>
 * Desc:httpClient工具类
 */
public class HttpClientUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static PoolingHttpClientConnectionManager phccm;
    public static String EMPTY_STR_RESULT = "";

    /**
     * 初始化
     */
    private static void init() {
       if (phccm == null) {
           phccm = new PoolingHttpClientConnectionManager();
           phccm.setMaxTotal(HttpClientConfig.MAX_TOTAL);
           phccm.setDefaultMaxPerRoute(HttpClientConfig.MAX_PER_ROUTE);
        }
    }


    /**
     * ͨ从连接池中获取httpClient对象
     *
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        init();
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= HttpClientConfig.RETRY_NUM) {
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 没响应
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// SSL处理异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 被打断
                    return false;
                }
                if (exception instanceof UnknownHostException) {// host不能识别
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 请求超时
                    return false;
                }
                if (exception instanceof SSLException) {
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };

        // 配置requestConfig
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(HttpClientConfig.CONNECT_TIMEOUT)
                .setSocketTimeout(HttpClientConfig.SOCKET_TIMEOUT)
                .setCookieSpec(CookieSpecs.BEST_MATCH).build();

        //重定向策略
        LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();

        return HttpClients.custom().setConnectionManager(phccm)
                                                    .setDefaultRequestConfig(requestConfig)
                                                    .setRedirectStrategy(redirectStrategy)
                                                    .setRetryHandler(httpRequestRetryHandler)
                                                    .build();
    }

    /**
     * get请求
     * @param requestUrl
     * @return
     */
    public static String get(String requestUrl) {
        HttpGet httpGet = new HttpGet(requestUrl);
        return getResult(httpGet);
    }

    /**
     * 参数get请求
     * @param requestUrl
     * @param params
     * @return
     * @throws URISyntaxException
     */
    public static String get(String requestUrl, Map<String, Object> params) throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setPath(requestUrl);

        ArrayList<NameValuePair> pairs = covertParamsToNVPList(params);
        ub.setParameters(pairs);

        HttpGet httpGet = new HttpGet(ub.build());
        return getResult(httpGet);
    }

    /**
     *
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws URISyntaxException
     */
    public static String get(String url, Map<String, Object> headers, Map<String, Object> params)
            throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);

        ArrayList<NameValuePair> pairs = covertParamsToNVPList(params);
        ub.setParameters(pairs);

        HttpGet httpGet = new HttpGet(ub.build());
        for (Map.Entry<String, Object> param : headers.entrySet()) {
            httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }
        return getResult(httpGet);
    }

    /**
     * post请求
     * @param url
     * @return
     */
    public static String post(String url) {
        HttpPost httpPost = new HttpPost(url);
        return getResult(httpPost);
    }

    /**
     *
     * @param url
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String post(String url, Map<String, Object> params) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        ArrayList<NameValuePair> pairs = covertParamsToNVPList(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, HttpClientConfig.ENCODING));
        return getResult(httpPost);
    }

    /**
     *
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String post(String url, Map<String, Object> headers, Map<String, Object> params)
            throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);

        for (Map.Entry<String, Object> param : headers.entrySet()) {
            httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }

        ArrayList<NameValuePair> pairs = covertParamsToNVPList(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, HttpClientConfig.ENCODING));

        return getResult(httpPost);
    }

    /**
     * 参数转换
     * @param params
     * @return
     */
    private static ArrayList<NameValuePair> covertParamsToNVPList(Map<String, Object> params) {
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
        }
        return pairs;
    }

    /**
     * 请求结果
     * @param request
     * @return
     */
    private static String getResult(HttpRequestBase request) {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != HttpStatus.SC_OK) {
                logger.error(request.getRequestLine().getUri()+"访问异常,״̬状态码:{},详情:{}",statusCode,response.getStatusLine().getReasonPhrase());
            }
            String url = request.getRequestLine().getUri();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return result;
            }
        } catch (ClientProtocolException e) {
            logger.error("请求异常",e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("请求异常",e);
            e.printStackTrace();
        } finally {
            if(response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("请求异常",e);
                    e.printStackTrace();
                }
            }
        }
        return EMPTY_STR_RESULT;
    }
}
