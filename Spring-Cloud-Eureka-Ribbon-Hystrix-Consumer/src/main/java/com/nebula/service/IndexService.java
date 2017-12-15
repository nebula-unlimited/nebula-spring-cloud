package com.nebula.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Hystrix 服务层接口实现类
 *
 * @author Nebula Unlimited
 */

@Service
public class IndexService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 定义服务降级逻辑
     * @return
     */
    @HystrixCommand(fallbackMethod = "fallback")
    public String index() {
        return restTemplate.getForObject("http://Spring-Cloud-Eureka-Client/hello", String.class);
    }

    public String fallback() {
        return "fallback";
    }
}
