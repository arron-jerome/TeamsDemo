package com.disney.teams.model.vo;

import java.io.Serializable;

public class BaseStatusCode implements Serializable {

    private static final long serialVersionUID = 968330307493536297L;

    protected String BaseCode = "0000";

    public void setBaseCode(String baseCode) {
        BaseCode = baseCode;
    }

    public final static String SUCCESS_CODE = "2000";
    public final static BaseStatusCode SUCCESS = new BaseStatusCode(SUCCESS_CODE, "");

    //	客户端常用错误码
    public static final String CLIENT_ERROR_CODE = "A001";
    public static final BaseStatusCode CLIENT_ERROR = new BaseStatusCode(CLIENT_ERROR_CODE, "Client Error");

    // 参数为空
    public static final String CLIENT_EMPTY_PARAMETER_CODE = "A002";
    public static final BaseStatusCode CLIENT_EMPTY_PARAMETER = new BaseStatusCode(CLIENT_EMPTY_PARAMETER_CODE, "Parameter Is Null");

    // 未登录
    public static final String CLIENT_NOT_LOGIN_CODE = "A003";
    public static final BaseStatusCode CLIENT_NOT_LOGIN = new BaseStatusCode(CLIENT_NOT_LOGIN_CODE, "Login Failed");

    //	访问拒绝
    public static final String CLIENT_ERROR_ACCESS_DENY_CODE = "A004";
    public static final BaseStatusCode CLIENT_ERROR_ACCESS_DENY = new BaseStatusCode(CLIENT_ERROR_ACCESS_DENY_CODE, "Access Deny");

    //	接口访问太频繁
    public static final String CLIENT_ERROR_ACCESS_TOO_FREQUENTLY_CODE = "A005";
    public static final BaseStatusCode CLIENT_ERROR_ACCESS_TOO_FREQUENTLY = new BaseStatusCode(CLIENT_ERROR_ACCESS_TOO_FREQUENTLY_CODE, "Access Too Frequently");

    // 重复提交
    public static final String CLIENT_ERROR_RESUBMIT_CODE = "A006";
    public static final BaseStatusCode CLIENT_ERROR_RESUBMIT = new BaseStatusCode(CLIENT_ERROR_RESUBMIT_CODE, "Already Submitted");

    //	服务器常用错误码
    public final static String SERVER_ERROR_CODE = "B001";
    public static final String SERVER_ERROR_MSG = "Server Error";
    public final static BaseStatusCode SERVER_ERROR = new BaseStatusCode(SERVER_ERROR_CODE, SERVER_ERROR_MSG);

    public final static String SERVER_BUSY_ERROR_CODE = "B002";
    public final static BaseStatusCode SERVER_BUSY_ERROR = new BaseStatusCode(SERVER_BUSY_ERROR_CODE, "Server Busy");

    // Third Partner Error
    public static final String TP_ERROR_CODE = "C001";
    public static final BaseStatusCode TP_ERROR = new BaseStatusCode(TP_ERROR_CODE, "Third partner Exception");

    // Database Error
    public static final String DATABASE_ERROR = "C001";
    public static final BaseStatusCode DATABASE_ERROR_DATABASE = new BaseStatusCode(DATABASE_ERROR, "Third partner Exception");

    // Cache Error
    public static final String CACHE_ERROR = "C003";
    public static final BaseStatusCode CACHE_ERROR_CACHE = new BaseStatusCode(CACHE_ERROR, "Cache Exception Type");

    private final String code;
    private final String message;

    public BaseStatusCode() {
        this.code = this.BaseCode + SERVER_ERROR_CODE;
        this.message = SERVER_ERROR_MSG;
    }

    public BaseStatusCode(String code, String message) {
        this.code = this.BaseCode + (code == null ? SERVER_ERROR_CODE : code);
        this.message = (message == null ? SERVER_ERROR_MSG : message);
    }

    public final String getCode() {
        return code;
    }

    public final String getMessage() {
        return message;
    }

    public String toString() {
        return code + "(" + message + ")";
    }
}
