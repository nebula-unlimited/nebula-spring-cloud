package com.nebula.web;

import org.springframework.beans.factory.annotation.Autowired;
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
    private RestTemplate restTemplate;

    @RequestMapping("/")
    public String index() {
        return restTemplate.getForObject("http://Spring-Cloud-Eureka-Client/hello", String.class);
    }
}
