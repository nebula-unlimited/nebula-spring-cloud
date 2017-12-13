package com.nebula.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/")
    public String index() {
        System.out.println(discoveryClient.getServices().toString());
        ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
        System.out.println("host: " + serviceInstance.getHost() + " serviceId: " + serviceInstance.getServiceId());
        return "ok";
    }

    @RequestMapping("hello")
    public String hello() {
        return "hello";
    }
}
