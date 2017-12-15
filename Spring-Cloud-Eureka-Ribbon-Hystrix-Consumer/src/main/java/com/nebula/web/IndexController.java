package com.nebula.web;

import com.nebula.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IndexService indexService;

    @RequestMapping("/")
    public String index() {
        String callback = indexService.index();
        System.out.println(callback);
        return callback;
    }
}
