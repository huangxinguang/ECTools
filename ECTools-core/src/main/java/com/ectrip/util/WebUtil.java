package com.ectrip.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunshine on 16/7/25.
 */
public class WebUtil {
    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);

    public static Boolean isWeChatRequest(HttpServletRequest request) {
        String agent = null;
        Boolean flag = Boolean.valueOf(false);

        Enumeration headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = (String) headers.nextElement();
            if ("User-Agent".equalsIgnoreCase(name)) {
                agent = request.getHeader(name).toLowerCase();
                flag = Boolean.valueOf(agent.contains("micromessenger"));
                break;
            }
        }
        return flag;
    }

    public static Boolean needReturnJson(HttpServletRequest request) {
        Boolean flag = Boolean.valueOf(false);

        String requestType = request.getHeader("X-Requested-With");
        if ((requestType != null) && (requestType.equalsIgnoreCase("XMLHttpRequest"))) {
            flag = Boolean.valueOf(true);
        }
        return flag;
    }


    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge, String path, String domain, Boolean secure) {
        try {
            name = URLEncoder.encode(name, "UTF-8");
            value = URLEncoder.encode(value, "UTF-8");
            Cookie cookie = new Cookie(name, value);
            if (maxAge != null) {
                cookie.setMaxAge(maxAge.intValue());
            }
            if (StringUtil.isNotEmpty(path)) {
                cookie.setPath(path);
            }
            if (StringUtil.isNotEmpty(domain)) {
                cookie.setDomain(domain);
            }
            if (secure != null) {
                cookie.setSecure(secure.booleanValue());
            }
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            logger.error("URL encoder异常:", e);
        }
    }

    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge) {
        addCookie(request, response, name, value, maxAge, "/", "", null);
    }

    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        addCookie(request, response, name, value, null, "/", "", null);
    }

    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            try {
                name = URLEncoder.encode(name, "UTF-8");
                for (Cookie cookie : cookies) {
                    if (name.equals(cookie.getName())) {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    }
                }
            } catch (UnsupportedEncodingException e) {
                logger.error("URL encoder异常:", e);
            }
        }
        return null;
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name, String path, String domain) {
        try {
            name = URLEncoder.encode(name, "UTF-8");
            Cookie cookie = new Cookie(name, null);
            cookie.setMaxAge(0);
            if (StringUtil.isNotEmpty(path)) {
                cookie.setPath(path);
            }
            if (StringUtil.isNotEmpty(domain)) {
                cookie.setDomain(domain);
            }
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            logger.error("URL encoder异常:", e);
        }
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        removeCookie(request, response, name, "/", "");
    }

    public static String getParameter(String queryString, String encoding, String name) {
        String[] parameterValues = (String[]) getParameterMap(queryString, encoding).get(name);
        return (parameterValues != null) && (parameterValues.length > 0) ? parameterValues[0] : null;
    }

    public static String[] getParameterValues(String queryString, String encoding, String name) {
        return (String[]) getParameterMap(queryString, encoding).get(name);
    }

    public static Map<String, String[]> getParameterMap(String queryString, String encoding) {
        Map<String, String[]> parameterMap = new HashMap();
        Charset charset = Charset.forName(encoding);
        if (StringUtil.isNotEmpty(queryString)) {
            byte[] bytes = queryString.getBytes(charset);
            if ((bytes != null) && (bytes.length > 0)) {
                int ix = 0;
                int ox = 0;
                String key = null;
                String value = null;
                while (ix < bytes.length) {
                    byte c = bytes[(ix++)];
                    switch ((char) c) {
                        case '&':
                            value = new String(bytes, 0, ox, charset);
                            if (key != null) {
                                putMapEntry(parameterMap, key, value);
                                key = null;
                            }
                            ox = 0;
                            break;
                        case '=':
                            if (key == null) {
                                key = new String(bytes, 0, ox, charset);
                                ox = 0;
                            } else {
                                bytes[(ox++)] = c;
                            }
                            break;
                        case '+':
                            bytes[(ox++)] = 32;
                            break;
                        case '%':
                            bytes[(ox++)] = ((byte) ((convertHexDigit(bytes[(ix++)]) << 4) + convertHexDigit(bytes[(ix++)])));
                            break;
                        default:
                            bytes[(ox++)] = c;
                    }
                }
                if (key != null) {
                    value = new String(bytes, 0, ox, charset);
                    putMapEntry(parameterMap, key, value);
                }
            }
        }
        return parameterMap;
    }

    private static void putMapEntry(Map<String, String[]> map, String name, String value) {
        String[] newValues = null;
        String[] oldValues = (String[]) map.get(name);
        if (oldValues == null) {
            newValues = new String[]{value};
        } else {
            newValues = new String[oldValues.length + 1];
            System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
            newValues[oldValues.length] = value;
        }
        map.put(name, newValues);
    }

    private static byte convertHexDigit(byte b) {
        if ((b >= 48) && (b <= 57)) {
            return (byte) (b - 48);
        }
        if ((b >= 97) && (b <= 102)) {
            return (byte) (b - 97 + 10);
        }
        if ((b >= 65) && (b <= 70)) {
            return (byte) (b - 65 + 10);
        }
        throw new IllegalArgumentException();
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("http_client_ip");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if ((ip != null) && (ip.indexOf(",") != -1)) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }
        return ip;
    }
}
