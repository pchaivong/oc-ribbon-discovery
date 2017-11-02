package com.kubecloud.ribbondiscovery;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pchaivong on 10/10/2017 AD.
 */

@RestController
public class RibbonDiscoveryController {

    private final String hostName = System.getenv("HOSTNAME");

    private RibbonDiscoveryService ribbonDiscoveryService;

    public RibbonDiscoveryController(RibbonDiscoveryService ribbonDiscoveryService){
        this.ribbonDiscoveryService = ribbonDiscoveryService;
    }

    @RequestMapping(value = "/entry", method = RequestMethod.GET)
    public String entryPoint(){
        return this.ribbonDiscoveryService.getHostname();
    }

    @RequestMapping(value = "/hostname", method = RequestMethod.GET)
    public String hostName(){
        return this.hostName;
    }
}
