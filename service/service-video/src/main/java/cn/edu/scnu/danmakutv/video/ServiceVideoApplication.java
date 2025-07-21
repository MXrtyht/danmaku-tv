package cn.edu.scnu.danmakutv.video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cn.edu.scnu.danmakutv"})
public class ServiceVideoApplication {
    public static void main (String[] args) {
        SpringApplication.run(ServiceVideoApplication.class, args);
    }
}
