package com.disney.teams.cache;


import com.disney.teams.exception.BasicRuntimeException;
import com.disney.teams.model.vo.BaseStatusCode;

/**
 * @author arron.zhou
 * @version 1.0.0
 * @date 2022/12/20
 * Description:
 * Modification  History:
 * Date         Author        Version        Description
 * ------------------------------------------------------
 * 2022/12/20       arron.zhou      1.0.0          create
 */
public class CacheRuntimeException extends BasicRuntimeException {

    private static final long serialVersionUID = -4104858855674878071L;

    public CacheRuntimeException() {
    }

    public CacheRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheRuntimeException(Throwable cause) {
        super(cause);
    }
}
