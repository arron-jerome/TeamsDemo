package com.disney.teams.service.impl;

import com.disney.teams.service.utils.Utils;
import com.disney.teams.dubbo.itf.IAddressService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

@DubboService
public class AddressService implements IAddressService {
    
    @Override
    public String findAddress(String name) {
        String hostName = Utils.getHostIp();
        return String.format("address service:%s param: %s findAddress----->request from consumer: %s"
                , hostName, name, RpcContext.getContext().getRemoteAddress());
    }

    @Override
    public String listAddress() {
        String hostName = Utils.getHostIp();
        return String.format("address service:%s listAddress----->request from consumer: %s"
                , hostName, RpcContext.getContext().getRemoteAddress());
    }
}
