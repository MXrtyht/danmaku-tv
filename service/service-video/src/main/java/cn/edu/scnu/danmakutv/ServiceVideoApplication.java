package cn.edu.scnu.danmakutv;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(scanBasePackages = {"cn.edu.scnu.danmakutv"})
@MapperScan(basePackages = "cn.edu.scnu.danmakutv.video.mapper")
public class ServiceVideoApplication {
    public static void main (String[] args) {
        SpringApplication.run(ServiceVideoApplication.class, args);
    }
}
