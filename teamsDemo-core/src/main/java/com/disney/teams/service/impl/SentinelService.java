package com.disney.teams.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.disney.teams.service.ISentinelService;
import com.disney.teams.service.logs.TraceContextUtils;
import com.disney.teams.service.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SentinelService implements ISentinelService {

    @Override
    @SentinelResource(value = "echo",fallback = "doFallback",blockHandler = "exceptionHandler")
    public String echo() {
        String hostName = Utils.getHostIp();
        String info = "traceId:" + TraceContextUtils.logLink()
                + "echo --> hostname:" + hostName;
        log.info(info);
        return info;
    }

    @Override
    @SentinelResource(value = "delay")
    public String delay() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String hostName = Utils.getHostIp();
        String info = "traceId:" + TraceContextUtils.logLink()
                + "delay --> hostname:" + hostName;
        log.info(info);
        return info;
    }

    public String doFallback(Throwable t) {
        return "fallback";
    }

    public String exceptionHandler(long s, BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "Oops, error occurred at " + s;
    }
}
