package com.disney.teams.common.exception;

import com.disney.teams.model.Msg;

import java.io.Serializable;

public class StatusCode implements Serializable {

    private static final long serialVersionUID = 968330307493536297L;

    public final static String SUCCESS_CODE = "2000";
    public final static StatusCode SUCCESS = new StatusCode(SUCCESS_CODE, "");

    //	客户端常用错误码
    public static final String CLIENT_ERROR_CODE = "00000400";
    public static final StatusCode CLIENT_ERROR = new StatusCode(CLIENT_ERROR_CODE, "Client Error");

    // 参数为空
    public static final String CLIENT_EMPTY_PARAMETER_CODE = "00000401";
    public static final StatusCode CLIENT_EMPTY_PARAMETER = new StatusCode(CLIENT_EMPTY_PARAMETER_CODE, "Parameter Is Null");

    // 参数非法
    public static final String CLIENT_ILLEGAL_PARAMETER_CODE = "00000402";
    public static final StatusCode CLIENT_ILLEGAL_PARAMETER = new StatusCode(CLIENT_ILLEGAL_PARAMETER_CODE, "Parameter Illeagal");

    // 未登录
    public static final String CLIENT_NOT_LOGIN_CODE = "00000403";
    public static final StatusCode CLIENT_NOT_LOGIN = new StatusCode(CLIENT_NOT_LOGIN_CODE, "Login Failed");

    //	访问拒绝
    public static final String CLIENT_ERROR_ACCESS_DENY_CODE = "00000404";
    public static final StatusCode CLIENT_ERROR_ACCESS_DENY = new StatusCode(CLIENT_ERROR_ACCESS_DENY_CODE, "Access Deny");

    //	接口访问太频繁
    public static final String CLIENT_ERROR_ACCESS_TOO_FREQUENTLY_CODE = "00000405";
    public static final StatusCode CLIENT_ERROR_ACCESS_TOO_FREQUENTLY = new StatusCode(CLIENT_ERROR_ACCESS_TOO_FREQUENTLY_CODE, "Access Too Frequently");

    // 重复提交
    public static final String CLIENT_ERROR_RESUBMIT_CODE = "00000406";
    public static final StatusCode CLIENT_ERROR_RESUBMIT = new StatusCode(CLIENT_ERROR_RESUBMIT_CODE, "Already Submitted");

    // 数据不存在
    public static final String CLIENT_DATA_NOT_EXISTS_CODE = "00000407";
    public static final StatusCode CLIENT_DATA_NOT_EXISTS = new StatusCode(CLIENT_DATA_NOT_EXISTS_CODE, "Data Not Exist");

    // 格式化错误
    public static final String CLIENT_PARAM_FORMAT_ERROR_CODE = "00000408";
    public static final StatusCode CLIENT_FORMAT_ERROR = new StatusCode(CLIENT_PARAM_FORMAT_ERROR_CODE, "Format Error");

    //	服务器常用错误码
    public final static String SERVER_ERROR_CODE = "00000500";
    public static final String SERVER_ERROR_MSG = "Server Error";
    public final static StatusCode SERVER_ERROR = new StatusCode(SERVER_ERROR_CODE, SERVER_ERROR_MSG);

    public final static String SERVER_BUSY_ERROR_CODE = "00000501";
    public final static StatusCode SERVER_BUSY_ERROR = new StatusCode(SERVER_BUSY_ERROR_CODE, "Server Busy");

    // Database Error
    public static final String DATABASE_ERROR = "50000502";
    public static final StatusCode SERVER_ERROR_DATABASE = new StatusCode(DATABASE_ERROR, "Database Exception Type");

    // Cache Error
    public static final String CACHE_ERROR = "50000503";
    public static final StatusCode SERVER_ERROR_CACHE = new StatusCode(DATABASE_ERROR, "Cache Exception Type");

    private final String code;
    private final String message;

    public StatusCode(String code, String message) {
        this.code = (code == null ? SERVER_ERROR_CODE : code);
        this.message = (message == null ? SERVER_ERROR_MSG : message);
    }

    public final String getCode() {
        return code;
    }

    public final String getMessage() {
        return message;
    }

    public final Msg msg() {
        return Msg.failed(this);
    }

    public String toString() {
        return code + "(" + message + ")";
    }

}
