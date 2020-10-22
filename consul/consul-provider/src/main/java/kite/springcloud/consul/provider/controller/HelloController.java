package kite.springcloud.consul.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * HelloController
 *
 * @author fengzheng
 * @date 2018/11/23
 */
@RestController
@Slf4j
public class HelloController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping(value = "test")
    public String test(){

        List<String> services = discoveryClient.getServices();
        for(String s : services){
            log.info(s);
        }
        return "hello spring cloud!" + applicationName;
    }

    @GetMapping(value = "nice")
    public String nice(){
        List<String> services = discoveryClient.getServices();
        for(String s : services){
            log.info(s);
        }
        return "nice to meet you!";
    }
}
