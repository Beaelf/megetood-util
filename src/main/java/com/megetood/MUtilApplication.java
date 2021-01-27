package com.megetood;

import com.megetood.example.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 启动类
 *
 * @author Lei Chengdong
 * @date 2020/12/4
 */
@SpringBootApplication
@RestController
public class MUtilApplication {
    public static void main(String[] args) {
        SpringApplication.run(MUtilApplication.class, args);
    }

    @GetMapping("/main/test")
    public User testGet(User user){
        return user;
    }

    @PostMapping("/main/test")
    public User test(@RequestBody User user){
        return user;
    }
}
