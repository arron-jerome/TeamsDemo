package com.fsk.gray.common.dubbo;

import org.apache.dubbo.config.annotation.DubboService;


public interface IUserService {

    //        // 当前应用配置
//        ApplicationConfig application = new ApplicationConfig();
//        application.setName("demo-provider");
//
//        // 连接注册中心配置
//        RegistryConfig registry = new RegistryConfig();
//        registry.setAddress("zookeeper://10.20.130.230:2181");
//
//        // 服务提供者协议配置
//        ProtocolConfig protocol = new ProtocolConfig();
//        protocol.setName("dubbo");
//        protocol.setPort(12345);
//        protocol.setThreads(200);
//
//        ServiceConfig<IDboService> fooServiceConfig = new ServiceConfig<>();
//        fooServiceConfig.setInterface(IDboService.class);
//        fooServiceConfig.setRef(new DboService());
//        fooServiceConfig.setVersion("1.0.0");

    String findUser(String name);

    String listUsers();
}