package com.nebula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Spring Cloud Eureka Client 启动类
 *
 * @author Nebula Unlimited
 */

@EnableDiscoveryClient
@SpringBootApplication
public class EurekaClient {
    public static void main(String[] args) {
        SpringApplication.run(EurekaClient.class, args);
    }
}
