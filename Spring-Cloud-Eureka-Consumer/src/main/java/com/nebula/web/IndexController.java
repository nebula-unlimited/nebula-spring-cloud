package com.nebula.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 首页控制器
 *
 * @author Nebula Unlimited
 */

@RestController
public class IndexController {
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/")
    public String index() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("Spring-Cloud-Eureka-Client");
        System.out.println(serviceInstance.toString());

        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/hello";
        System.out.println(url);

        return "调用 " + url + ": " + restTemplate.getForObject(url, String.class);
    }
}
