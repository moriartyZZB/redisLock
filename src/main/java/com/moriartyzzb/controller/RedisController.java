package com.moriartyzzb.controller;

import com.moriartyzzb.thread.MyThread;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zengzhibin
 * @title: RedisController
 * @projectName redisLock
 * @description: TODO
 * @date 2021/2/26 16:47
 */
@RestController
@RequestMapping("/redisLock")
public class RedisController {
    @GetMapping("/test")
    public String redisLock() {
        for (int i = 1; i < 16; i++) {
            MyThread myThread = new MyThread();
            myThread.setName2(i+"");
            myThread.start();
        }
        return "OK";
    }
}
