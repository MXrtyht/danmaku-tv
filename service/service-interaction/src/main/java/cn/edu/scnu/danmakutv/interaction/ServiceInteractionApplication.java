package cn.edu.scnu.danmakutv.interaction;

import cn.edu.scnu.danmakutv.interaction.websocket.WebSocketService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"cn.edu.scnu.danmakutv"})
@EnableAsync
@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceInteractionApplication {
    public static void main (String[] args) {
        ApplicationContext app = SpringApplication.run(ServiceInteractionApplication.class, args);
        WebSocketService.setApplicationContext(app);
    }
}
