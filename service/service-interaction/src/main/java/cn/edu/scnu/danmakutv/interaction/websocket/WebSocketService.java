package cn.edu.scnu.danmakutv.interaction.websocket;

import cn.edu.scnu.common.utils.RocketMQUtil;
import cn.edu.scnu.danmakutv.common.authentication.JwtHelper;
import cn.edu.scnu.danmakutv.domain.interaction.Danmaku;
import cn.edu.scnu.danmakutv.interaction.service.DanmakuService;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.StringUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.Getter;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Component
@ServerEndpoint("/wsserver/{token}")
public class WebSocketService {

    public static final ConcurrentHashMap<String, WebSocketService> WEBSOCKET_MAP = new ConcurrentHashMap<>();
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);
    private static ApplicationContext APPLICATION_CONTEXT;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Getter
    private Session session;

    @Getter
    private String sessionId;
    private Long userId;

    public static void setApplicationContext (ApplicationContext applicationContext) {
        WebSocketService.APPLICATION_CONTEXT = applicationContext;
    }

    // 统一的消息发送方法
    private void sendUnifiedMessage (String type, String data, String message) throws IOException {
        JSONObject response = new JSONObject();
        response.put("type", type);
        response.put("data", data);
        response.put("message", message);
        response.put("time", LocalDateTime.now());
        this.sendMessage(response.toJSONString());
    }

    // 建立连接
    @OnOpen
    public void openConnection (Session session, @PathParam("token") String token) {
        this.session = session;
        this.sessionId = session.getId();

        // 未登录时没有 userId
        try {
            this.userId = JwtHelper.getUserId(token);
        } catch (Exception ignored) {
        }

        // 有旧的连接 则移除
        if (WEBSOCKET_MAP.containsKey(sessionId)) {
            WEBSOCKET_MAP.remove(sessionId);
            WEBSOCKET_MAP.put(sessionId, this);
        } else {
            // 新的连接 则添加, 且在线人数加1
            WEBSOCKET_MAP.put(sessionId, this);
            ONLINE_COUNT.getAndIncrement();
        }

        // 日志记录
        logger.info("用户连接成功：" + sessionId + ", 当前在线人数为：" + ONLINE_COUNT.get());

        // 通知前端连接成功和当前在线人数
        try {
            sendUnifiedMessage(
                    "connection",
                    "会话id: " + sessionId + " 用户id: " + userId + " 在线人数: " + ONLINE_COUNT.get(),
                    "连接成功"
            );
        } catch (Exception e) {
            logger.error("连接异常", e);
        }
    }

    // 取消连接
    @OnClose
    public void closeConnection () {
        // 移除连接
        if (WEBSOCKET_MAP.containsKey(sessionId)) {
            WEBSOCKET_MAP.remove(sessionId);
            ONLINE_COUNT.getAndDecrement();
        }
        logger.info("用户连接关闭：" + sessionId + ", 当前在线人数为：" + ONLINE_COUNT.get());
    }

    @OnMessage
    public void onMessage (String message) {
        logger.info("用户信息：" + sessionId + ", 报文：" + message);
        if (!StringUtil.isNullOrEmpty(message)) {
            try {
                // 群发弹幕消息
                for (WebSocketService webSocketService : WEBSOCKET_MAP.values()) {
                    if (webSocketService.session.isOpen()) {
                        // 先解析统一格式的消息
                        JSONObject jsonObject = JSONObject.parseObject(message);
                        if (jsonObject.getString("type").equals("danmaku")) {
                            String danmakuContent = jsonObject.getString("data");

                            // 发送到 RocketMQ
                            DefaultMQProducer danmakuProducer = (DefaultMQProducer) APPLICATION_CONTEXT.getBean("danmakuProducer");

                            JSONObject mqMessage = new JSONObject();
                            mqMessage.put("sessionId", webSocketService.getSessionId());
                            mqMessage.put("message", danmakuContent);

                            Message msg = new Message("Topic-Danmaku", mqMessage.toJSONString().getBytes(StandardCharsets.UTF_8));
                            RocketMQUtil.asyncSendMsg(danmakuProducer, msg);
                        }
                    }
                }

                // 登陆的用户才可发弹幕
                if (this.userId != null) {
                    // 保存弹幕到数据库
                    // 先解析统一格式的消息
                    JSONObject jsonObject = JSONObject.parseObject(message);
                    if (jsonObject.getString("type").equals("danmaku")) {
                        String danmakuContent = jsonObject.getString("data");
                        Danmaku danmaku = JSONObject.parseObject(
                                danmakuContent,
                                Danmaku.class
                        );
                        danmaku.setUserId(userId);
                        danmaku.setCreateAt(LocalDateTime.now());

                        DanmakuService danmakuService = APPLICATION_CONTEXT.getBean(DanmakuService.class);
                        danmakuService.addDanmaku(danmaku);
                        // 保存弹幕到redis
                        danmakuService.addDanmakuToRedis(danmaku);
                    }
                }

            } catch (Exception e) {
                logger.error("弹幕接收出现问题", e);
                // 发送错误消息
                try {
                    sendUnifiedMessage("error", e.getMessage(), "弹幕接收错误");
                } catch (IOException ioException) {
                    logger.error("发送错误消息失败", ioException);
                }
            }
        }
    }

    @OnError
    public void onError (Throwable error) {
        logger.error("WebSocket 错误", error);
        try {
            sendUnifiedMessage("error", null, "连接发生错误");
        } catch (IOException e) {
            logger.error("发送错误消息失败", e);
        }
    }

    // 定时任务，通知前端在线人数
    @Scheduled(fixedRate = 10000)
    private void noticeOnlineCount () throws IOException {
        for (WebSocketService webSocketService : WEBSOCKET_MAP.values()) {
            if (webSocketService.session.isOpen()) {
                webSocketService.sendUnifiedMessage(
                        "connection",
                        String.valueOf(ONLINE_COUNT.get()),
                        "在线人数更新"
                );
            }
        }
    }

    public void sendMessage (String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
}