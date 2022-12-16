package com.disney.teamsDemo.model.exception;


import com.disney.teams.model.vo.BaseStatusCode;

public class StatusCode extends BaseStatusCode {

    private static final long serialVersionUID = 35898123493536297L;

    protected String BaseCode = "1000";

    public static final StatusCode CHANGE_PROFILE_FAILED = new StatusCode("B100"
            , "change profile failed");

    public StatusCode() {
        super();
    }

    public StatusCode(String code, String message) {
        super(code, message);
    }
}