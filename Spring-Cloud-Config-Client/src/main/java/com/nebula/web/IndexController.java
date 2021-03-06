package com.nebula.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页控制器
 *
 * @author Nebula Unlimited
 */

@RestController
public class IndexController {
    @Value("${from}")
    private String from;

    @RequestMapping("/")
    public String index() {
        return from;
    }
}
