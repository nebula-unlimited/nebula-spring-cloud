package com.nebula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Spring Cloud Eureka Ribbon Hystrix Consumer 启动类
 *
 * @author Nebula Unlimited
 */

@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class Application {
    /**
     * 开启负载均衡客户端
     */
    @LoadBalanced
    /**
     * 注册一个具有容错功能的 RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
