package cn.edu.scnu.danmakutv.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Component
@ServerEndpoint("/wsserver")
public class WebSocketService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    private static final ConcurrentHashMap<String, WebSocketService> WEBSOCKET_MAP = new ConcurrentHashMap<>();

    private Session session;

    private String sessionId;

    private static ApplicationContext APPLICATION_CONTEXT;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketService.APPLICATION_CONTEXT = applicationContext;
    }

    // 建立连接
    @OnOpen
    public void openConnection(Session session){
        this.session = session;
        this.sessionId = session.getId();

        // 有旧的连接 则移除
        if(WEBSOCKET_MAP.containsKey(sessionId)){
            WEBSOCKET_MAP.remove(sessionId);
            WEBSOCKET_MAP.put(sessionId, this);
        }else{
            // 新的连接 则添加, 且在线人数加1
            WEBSOCKET_MAP.put(sessionId, this);
            ONLINE_COUNT.getAndIncrement();
        }

        // 日志记录
        logger.info("用户连接成功：" + sessionId + ", 当前在线人数为：" + ONLINE_COUNT.get());

        // 通知前端
        try{
            this.sendMessage("成功");
        }catch (Exception e){
            logger.error("连接异常", e);
        }
    }

    // 取消连接
    @OnClose
    public void closeConnection(){
        // 移除连接
        if(WEBSOCKET_MAP.containsKey(sessionId)){
            WEBSOCKET_MAP.remove(sessionId);
            ONLINE_COUNT.getAndDecrement();
        }
        logger.info("用户连接关闭：" + sessionId + ", 当前在线人数为：" + ONLINE_COUNT.get());
    }

    @OnMessage
    public void onMessage(String message){

    }

    @OnError
    public void onError(Throwable error){

    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
}
