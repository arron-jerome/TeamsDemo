package com.disney.teams.app.configuration;

import com.alibaba.csp.sentinel.adapter.dubbo.config.DubboAdapterGlobalConfig;
import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallback;
import com.alibaba.csp.sentinel.adapter.dubbo.origin.DubboOriginParser;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.fsk.gray.common.logs.rid.TraceContextUtils;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Properties;


@org.springframework.context.annotation.Configuration
public class GrayConfiguration implements InitializingBean {
    //    @Bean
//    @SentinelRestTemplate(blockHandler = "handleException"
//            , blockHandlerClass = ExceptionUtil.class)
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
    @Value("${spring.cloud.nacos.config.server-addr}")
    String remoteAddress;

    @Value("${spring.cloud.nacos.config.namespace}")
    String namespace;

    String groupId = "sentinel";

    final String dataId = "com.alibaba.csp.sentinel.gray-server.flow.rule";

    public void initSentinel() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, remoteAddress);
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(properties
                , groupId, dataId,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                }));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());

        try {
            final ConfigService configService = NacosFactory.createConfigService(properties);
            WritableDataSourceRegistry.registerFlowDataSource(new WritableDataSource<List<FlowRule>>() {
                @Override
                public void write(List<FlowRule> value) throws Exception {
                    String content = null;
                    if (value != null) {
                        content = JSON.toJSONString(value);
                    }
                    if (content == null) {
                        configService.removeConfig(dataId, groupId);
                    } else {
                        configService.publishConfig(dataId, groupId, content);
                    }
                }

                @Override
                public void close() throws Exception {

                }
            });
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    public void initDubboSentinel() {
        DubboAdapterGlobalConfig.setProviderFallback(new DubboFallback() {
            @Override
            public Result handle(Invoker<?> invoker, Invocation invocation, BlockException ex) {
                String attachment = invocation.getAttachment(TraceContextUtils.RID_HTTP_HEADER_KEY);
                return null;
            }
        });
        DubboAdapterGlobalConfig.setOriginParser(new DubboOriginParser() {
            @Override
            public String parse(Invoker<?> invoker, Invocation invocation) {
                return null;
            }
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initSentinel();
        initDubboSentinel();
    }
}
