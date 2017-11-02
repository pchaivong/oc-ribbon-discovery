package com.kubecloud.ribbondiscovery;

import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfigKey;
import com.netflix.loadbalancer.*;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.openshift.client.OpenShiftClient;
import io.fabric8.spring.cloud.kubernetes.ribbon.KubernetesServerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


/**
 * Created by pchaivong on 10/10/2017 AD.
 */

public class RibbonConfiguration {

    @Autowired
    IClientConfig iClientConfig;

    @Bean
    public IPing ribbonPing(IClientConfig config){
        return new PingUrl();
    }

    @Bean
    public IRule ribbonRule(IClientConfig config){
        return new WeightedResponseTimeRule();
    }

    @Bean
    public IClientConfig clientConfig(){
        return IClientConfig.Builder.newBuilder()
                .withDefaultValues()
                .build()
                .set(IClientConfigKey.Keys.NIWSServerListClassName, KubernetesServerList.class.getName());
    }

    @Bean
    public DelayConfiguration delayConfiguration(){
        return new DelayConfiguration();
    }

}
