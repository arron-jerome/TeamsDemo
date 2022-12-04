package com.disney.teams.service.impl;

import com.disney.teams.service.utils.Utils;
import com.fsk.gray.common.dubbo.IUserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

@DubboService(version = "1.0.0", group = "user", timeout = 5000)
public class UserService implements IUserService {
    @Override
    public String findUser(String name) {
        String hostName = Utils.getHostIp();
        return String.format("user service:%s param: %s findUser----->request from consumer: %s"
                , hostName, name, RpcContext.getContext().getRemoteAddress());
    }

    @Override
    public String listUsers() {
        String hostName = Utils.getHostIp();
        return String.format("user service:%s listUsers----->request from consumer: %s"
                , hostName, RpcContext.getContext().getRemoteAddress());
    }
}