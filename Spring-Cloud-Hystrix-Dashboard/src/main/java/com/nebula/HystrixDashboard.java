package com.nebula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * Spring Cloud Hystrix Dashboard 启动类
 *
 * @author Nebula Unlimited
 */

@EnableHystrixDashboard
@SpringBootApplication
public class HystrixDashboard {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboard.class, args);
    }
}
