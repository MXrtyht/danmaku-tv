package cn.edu.scnu.danmakutv.user;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cn.edu.scnu.danmakutv"})
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceUserApplication {
    public static void main (String[] args) {
        // UserMomentsController中，需要用到ApplicationContext，来访问RocketMQConfig中声明的Bean（by JK）
         ApplicationContext app = SpringApplication.run(ServiceUserApplication.class, args);
    }
}
