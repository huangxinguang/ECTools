package com.ectrip.exception;

/**
 * Created by sunshine on 16/7/22.
 */
public abstract interface ResultCode {
    /**
     * 失败编码
     */
    public static final String COMMON_ERROR = "999999";


    public static final String COMMON_SUCCESS = "000000";

    public abstract String getCode();
}
