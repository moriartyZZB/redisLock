package com.moriartyzzb.thread;

import com.moriartyzzb.util.RedisLock;
import com.moriartyzzb.util.SpringBeanUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


/**
 * @author Zengzhibin
 * @title: MyThread
 * @projectName acitve-mq-project
 * @description: TODO
 * @date 2021/2/25 17:49
 */
@Component
public class MyThread extends Thread {

    private String name;
    public String getName2() { return name; }
    public void setName2(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        RedisLock redisLock = SpringBeanUtil.getBean(RedisLock.class);
        StringRedisTemplate redisTemplate= SpringBeanUtil.getBean(StringRedisTemplate.class);
        Boolean flag=false;
        try {
             flag = redisLock.lock("key_lock");
             if (flag){
                 int testV= Integer.valueOf(redisTemplate.opsForValue().get("testV"));
                 System.out.println(name+"【"+testV+"】");
                 if(testV>0){
                     redisTemplate.opsForValue().set("testV",testV-1+"");
                     System.out.println(name+"操作成功");
                 }else{
                     System.out.println(name+"操作失败");
                 }
             }else{
                 System.out.println(name+"获取锁失败");
             }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (flag){
                redisLock.unlock("key_lock");
            }

        }

    }
}
