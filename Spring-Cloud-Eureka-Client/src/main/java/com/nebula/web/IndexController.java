package com.nebula.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页控制器
 *
 * @author Nebula Unlimited
 */

@RestController
public class IndexController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/")
    public String index() {
        String callback = "index: " + discoveryClient.getServices();
        System.out.println(callback);
        return callback;
    }

    @RequestMapping("hello")
    public String hello() {
        /**
         * 为了触发 Spring-Cloud-Eureka-Ribbon-Hystrix-Consumer 项目的服务降级逻辑
         * 将服务提供者 Spring-Cloud-Eureka-Client 的逻辑加一些延迟
         */
        ///Thread.sleep(5000L);

        String callback = "hello: " + discoveryClient.getServices();
        System.out.println(callback);
        return callback;
    }
}
