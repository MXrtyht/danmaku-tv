package cn.edu.scnu.danmakutv.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cn.edu.scnu.danmakutv", "cn.edu.scnu.danmakutv.common.exception"})
public class ServiceUserApplication {
    public static void main (String[] args) {
        SpringApplication.run(ServiceUserApplication.class, args);
    }
}
