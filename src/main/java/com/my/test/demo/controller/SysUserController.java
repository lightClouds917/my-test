package com.my.test.demo.controller;

import com.my.test.demo.config.Producer1;
import com.my.test.demo.mongoentity.Order;
import com.my.test.demo.entity.SysUser;
import com.my.test.demo.listener.MyHttpSessionListener;
import com.my.test.demo.service.OrderService;
import com.my.test.demo.service.RedisService;
import com.my.test.demo.service.SysUserService;
import com.my.test.demo.util.CodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Description:
 * @author:dangshilin
 * @date 2019/2/2211:35
 */
@RestController
@RequestMapping("user")
@Slf4j
public class SysUserController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SysUserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private Producer1 producer1;

    @PostMapping("/add")
    public void insert(@RequestBody SysUser user){
        userService.insert(user);
   }

   @GetMapping("/list")
   public String getList(){
        List<SysUser> userList = userService.getUserList();
       for (SysUser user : userList) {
            producer1.producer1(user);
            try{
                Thread.sleep(2000L);
            }catch (InterruptedException e){
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
       }
        return userList.toString();
   }

   @GetMapping("/add-redis")
   public void addRedis(){
       List<SysUser> userList = userService.getUserList();
       String s = userList.toString();
       redisService.setValue("userList3:",s);
   }

    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute("zxc", "zxc");
        return  "index";
    }

    @GetMapping("/online")
    public String online() {
        return  "当前在线人数：" + MyHttpSessionListener.online + "人";
    }

    @GetMapping("/get_name")
    public String getByName(@RequestParam String realName) {
        return realName;
    }

    @PostMapping("/order/add")
    public void addOrder(){
        try {
            Order order = new Order();
            order.setId(121L);
            order.setOrderNo(CodeUtil.getOrderNoCode("RO"));
            order.setAmount(new BigDecimal(100));
            order.setProductName("书籍");
            order.setState(0);
            order.setUserId(1L);
            orderService.save(order);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
    }
}
