package com.kubecloud.ribbondiscovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * Created by pchaivong on 10/10/2017 AD.
 */

@RefreshScope
@RestController
public class RibbonDiscoveryController {

    private final String hostName = System.getenv("HOSTNAME");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${ribbon-discovery.delayed: 0}")
    private long delayed;

    private RibbonDiscoveryService ribbonDiscoveryService;

    public RibbonDiscoveryController(RibbonDiscoveryService ribbonDiscoveryService){
        this.ribbonDiscoveryService = ribbonDiscoveryService;
    }

    @RequestMapping(value = "/entry", method = RequestMethod.GET)
    public String entryPoint(){
        logger.info("Get hostname from service");
        long start = System.currentTimeMillis();
        String resp = this.ribbonDiscoveryService.getHostname();
        long delayed = System.currentTimeMillis() - start;
        logger.info("Time usage: " + delayed + " ms");
        return resp + "\n Time usage: " + delayed;
    }

    @RequestMapping(value = "/hostname", method = RequestMethod.GET)
    public String hostName(){

        try {
            logger.info("Sleep for: " + delayed + " seconds");
            Thread.sleep(delayed * 1000);

        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return this.hostName;
    }

    @RequestMapping(value = "/server-lb", method = RequestMethod.GET)
    public String serverLb(){
        logger.info("Consume services by Server side load balancing");


        RestTemplate template = new RestTemplate();
        long start = System.currentTimeMillis();
        String resp = template.getForObject("http://oc-ribbon-discovery/hostname", String.class);
        long delayed = System.currentTimeMillis() - start;
        logger.info("Time usage: " + delayed + " ms");
        return resp + "\n Time usage: " + delayed;

    }
}
