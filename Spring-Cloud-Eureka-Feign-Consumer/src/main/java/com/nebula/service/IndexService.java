package com.nebula.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Feign 服务层接口
 *
 * @author Nebula Unlimited
 */

@FeignClient("Spring-Cloud-Eureka-Client")
public interface IndexService {
    /**
     * index
     * @return
     */
    @GetMapping("hello")
    String index();
}
