package cn.edu.scnu.danmakutv;

import cn.edu.scnu.danmakutv.websocket.WebSocketService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ServiceInteractionApplication {
    public static void main (String[] args) {
        ApplicationContext app = SpringApplication.run(ServiceInteractionApplication.class, args);
        WebSocketService.setApplicationContext(app);
    }
}
