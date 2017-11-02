package com.kubecloud.ribbondiscovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by pchaivong on 10/10/2017 AD.
 */

@Service
public class RibbonDiscoveryService {

    @Autowired
    private RestTemplate restTemplate;

    public RibbonDiscoveryService(RestTemplate template){
        this.restTemplate = template;
    }


    public String getHostname(){
        return this.restTemplate.getForObject("http://oc-ribbon-discovery/hostname", String.class);
    }

    public String fallback(){
        return "Circuit Open";
    }
}
