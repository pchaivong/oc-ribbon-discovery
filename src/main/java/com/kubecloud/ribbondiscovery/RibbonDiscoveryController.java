package com.kubecloud.ribbondiscovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created by pchaivong on 10/10/2017 AD.
 */

@RestController
public class RibbonDiscoveryController {

    private final String hostName = System.getenv("HOSTNAME");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RibbonDiscoveryService ribbonDiscoveryService;

    public RibbonDiscoveryController(RibbonDiscoveryService ribbonDiscoveryService){
        this.ribbonDiscoveryService = ribbonDiscoveryService;
    }

    @RequestMapping(value = "/entry", method = RequestMethod.GET)
    public String entryPoint(){
        logger.info("Get hostname from service");
        long start = System.currentTimeMillis();
        String resp = this.ribbonDiscoveryService.getHostname();
        logger.info("Time usage: " + (System.currentTimeMillis()-start) + " ms");
        return resp;
    }

    @RequestMapping(value = "/hostname", method = RequestMethod.GET)
    public String hostName(){

        try {
            int delay = new Random().nextInt(6);
            logger.info("Sleep for: " + delay + " seconds");
            Thread.sleep(delay * 1000);

        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return this.hostName;
    }
}
