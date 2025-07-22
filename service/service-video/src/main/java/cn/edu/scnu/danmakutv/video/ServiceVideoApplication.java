package cn.edu.scnu.danmakutv.video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"cn.edu.scnu.danmakutv"})
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceVideoApplication {
    public static void main (String[] args) {
        SpringApplication.run(ServiceVideoApplication.class, args);
    }
}
