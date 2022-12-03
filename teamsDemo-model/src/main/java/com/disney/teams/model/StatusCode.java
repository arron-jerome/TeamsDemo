/*
 * Copyright 2016-2020 1HAITAO.COM. All rights reserved.
 */
package com.disney.teams.model;

import java.io.Serializable;

public class StatusCode implements Serializable {

    private static final long serialVersionUID = 968330307493536297L;
    
    public final static String SUCCESS_CODE = "2000";
	public final static StatusCode SUCCESS = new StatusCode(SUCCESS_CODE, "");

	//	客户端常用错误码
	public static final String CLIENT_ERROR_CODE = "40000000";
	public static final StatusCode CLIENT_ERROR = new StatusCode(CLIENT_ERROR_CODE,"Client Error");

	//	参数错误
	@Deprecated
	public static final String CLIENT_ERROR_PARAMETER_CODE = "40000001";
	@Deprecated
	public static final StatusCode CLIENT_ERROR_PARAMETER = new StatusCode(CLIENT_ERROR_PARAMETER_CODE,"Unknown Parameters");

	//	接口访问太频繁
	public static final String CLIENT_ERROR_ACCESS_TOO_FREQUENTLY_CODE = "40000002";
	public static final StatusCode CLIENT_ERROR_ACCESS_TOO_FREQUENTLY = new StatusCode(CLIENT_ERROR_ACCESS_TOO_FREQUENTLY_CODE,"Access Too Frequently");

	//	接口访问超时
	public static final String CLIENT_ERROR_ACCESS_TIMEOUT = "40000003";
	public static final StatusCode CLIENT_ERROR_ACCESS_TIMEOUT_PARAMETER = new StatusCode(
		CLIENT_ERROR_ACCESS_TIMEOUT,"Access Timeout");
	//	访问拒绝
	public static final String CLIENT_ERROR_ACCESS_DENY_CODE = "40000004";
	public static final StatusCode CLIENT_ERROR_ACCESS_DENY = new StatusCode(
		CLIENT_ERROR_ACCESS_DENY_CODE,"Access Deny");
	// 重复提交
	public static final String CLIENT_ERROR_RESUBMIT_CODE = "40000005";
	public static final StatusCode CLIENT_ERROR_RESUBMIT = new StatusCode(
		CLIENT_ERROR_RESUBMIT_CODE,"Already Submitted");
	// 参数为空
	public static final String CLIENT_EMPTY_PARAMETER_CODE = "40000006";
	public static final StatusCode CLIENT_EMPTY_PARAMETER = new StatusCode(CLIENT_EMPTY_PARAMETER_CODE,"Parameter Is Null");
	// 参数非法
	public static final String CLIENT_ILLEGAL_PARAMETER_CODE = "40000007";
	public static final StatusCode CLIENT_ILLEGAL_PARAMETER = new StatusCode(CLIENT_ILLEGAL_PARAMETER_CODE,"Parameter Illeagal");
	// 数据不存在
	public static final String CLIENT_DATA_NOT_EXISTS_CODE = "40000008";
	public static final StatusCode CLIENT_DATA_NOT_EXISTS = new StatusCode(CLIENT_DATA_NOT_EXISTS_CODE,"Data Not Exist");
	// 未登录
	public static final String CLIENT_NOT_LOGIN_CODE = "40000009";
	public static final StatusCode CLIENT_NOT_LOGIN = new StatusCode(CLIENT_NOT_LOGIN_CODE,"Not Login");
	// 解码错误
	public static final String CLIENT_DECODE_ERROR_CODE = "40000010";
	public static final StatusCode CLIENT_DECODE_ERROR = new StatusCode(CLIENT_DECODE_ERROR_CODE,"Decode Error");
	// 格式化错误
	public static final String CLIENT_PARAM_FORMAT_ERROR_CODE = "40000011";
	public static final StatusCode CLIENT_FORMAT_ERROR = new StatusCode(CLIENT_PARAM_FORMAT_ERROR_CODE,"Format Error");
	// 参数校验失败
	public static final String CLIENT_PARAM_VALIDATE_ERROR_CODE = "40000012";
	public static final StatusCode CLIENT_PARAM_VALIDATE_ERROR = new StatusCode(CLIENT_PARAM_VALIDATE_ERROR_CODE,"Validate Error");

//	服务器常用错误码
	public final static String SERVER_ERROR_CODE = "50000000";
	public static final String SERVER_ERROR_MSG =  "Server Error";
	public final static StatusCode SERVER_ERROR = new StatusCode(SERVER_ERROR_CODE, SERVER_ERROR_MSG);

	public final static String SERVER_BUSY_ERROR_CODE = "50000001";
	public final static StatusCode SERVER_BUSY_ERROR = new StatusCode(SERVER_BUSY_ERROR_CODE,"Server Busy");

	//HTTP链接错误，包括错误，超时等
	public static final String HTTP_CONNECT_ERROR_CODE = "50000002";
	public static final StatusCode HTTP_CONNECT_ERROR = new StatusCode(HTTP_CONNECT_ERROR_CODE,"HTTP Connect Error");

	//HTTP返回Code不正确
	public static final String HTTP_RESPONSE_CODE_ERROR_CODE = "50000003";
	public static final StatusCode HTTP_RESPONSE_CODE_ERROR = new StatusCode(HTTP_RESPONSE_CODE_ERROR_CODE,"HTTP Response Code  Error");

	//未知的异常类型
	public static final String UNKNOWN_EXCEPTION_TYPE_ERROR_CODE = "50000004";
	public static final StatusCode UNKNOWN_EXCEPTION_TYPE_ERROR = new StatusCode(UNKNOWN_EXCEPTION_TYPE_ERROR_CODE,"Unknown Exception Type");

	//未知的图片类型
	public static final String UNKNOWN_IMAGE_TYPE_ERROR_CODE = "50000005";
	public static final StatusCode UNKNOWN_IMAGE_TYPE_ERROR = new StatusCode(UNKNOWN_IMAGE_TYPE_ERROR_CODE,"Unknown Image Type");

	private final String code;
	private final String message;
	
	public StatusCode(String code, String message){
		this.code = (code == null ? SERVER_ERROR_CODE : code);
		this.message = (message == null ? SERVER_ERROR_MSG : message);
	}

	public final String getCode() {
		return code;
	}

	public final String getMessage() {
		return message;
	}

	public final Msg msg(){
		return Msg.failed(this);
	}

	public String toString(){
		return code + "(" + message + ")";
	}

}
