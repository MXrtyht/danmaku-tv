package cn.edu.scnu.danmakutv.minio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cn.edu.scnu.danmakutv"})
public class ServiceMinioApplication {
    public static void main (String[] args) {
        SpringApplication.run(ServiceMinioApplication.class, args);
    }
}
