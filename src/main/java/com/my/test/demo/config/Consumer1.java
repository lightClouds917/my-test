package com.my.test.demo.config;

import com.my.test.demo.entity.SysUser;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

/**
 * @Description:
 * @author:dangshilin
 * @date 2019/3/2810:37
 */
@Configuration
@RabbitListener(queues = "queue1")
public class Consumer1 {

    /**
     * 消费消息
     * @param user
     */
    @RabbitHandler
    public  void process1(SysUser user){
        System.out.println("Consumer1" + LocalTime.now() + "********" + user.toString());
    }
}
