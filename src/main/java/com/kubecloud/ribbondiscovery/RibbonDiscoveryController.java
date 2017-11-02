package com.kubecloud.ribbondiscovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * Created by pchaivong on 10/10/2017 AD.
 */

@RestController
public class RibbonDiscoveryController {

    private final String hostName = System.getenv("HOSTNAME");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private RibbonDiscoveryService ribbonDiscoveryService;
    private DelayConfiguration delayConfiguration;

    @Autowired
    public RibbonDiscoveryController(RibbonDiscoveryService ribbonDiscoveryService,
                                     DelayConfiguration delayConfiguration){
        this.ribbonDiscoveryService = ribbonDiscoveryService;
        this.delayConfiguration = delayConfiguration;
        this.delayConfiguration.setDelayed(0);
    }

    @RequestMapping(value = "/delayed/{ms}", method = RequestMethod.GET)
    public String setDelay(@PathVariable long ms){
        this.delayConfiguration.setDelayed(ms);
        return hostName + " is delayed for " + ms + " ms";
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
            logger.info("Sleep for: " + delayConfiguration.getDelayed() + " ms");
            Thread.sleep(delayConfiguration.getDelayed());

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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String ping(){
        try {
            Thread.sleep(delayConfiguration.getDelayed());
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return "OK";
    }
}
