package cn.edu.scnu.danmakutv.user.config;

import cn.edu.scnu.danmakutv.domain.user.UserMoments;
import cn.edu.scnu.danmakutv.user.constant.UserMomentsConstant;
import cn.edu.scnu.danmakutv.user.service.UserFollowService;
import cn.edu.scnu.danmakutv.vo.user.UserFanDTO;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.StringUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserServiceRocketMQConfig {
    // 记得在properties中更改对应的值
    @Value("${rocketmq.name.server.address}")
    private String nameServerAddr;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserFollowService userFollowService;

    @Bean("momentsProducer")
    public DefaultMQProducer momentsProducer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.GROUP_MOMENTS);
        producer.setNamesrvAddr(nameServerAddr);
        producer.start();
        return producer;
    }

    @Bean("momentsConsumer")
    public DefaultMQPushConsumer momentsConsumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.GROUP_MOMENTS);
        consumer.setNamesrvAddr(nameServerAddr);
        consumer.subscribe(UserMomentsConstant.TOPIC_MOMENTS, "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt msg = msgs.get(0);
                if(msg == null)
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                String bodyStr = new String(msg.getBody());
                UserMoments userMoments = JSONObject.toJavaObject(JSONObject.parseObject(bodyStr), UserMoments.class);
                Long userId = userMoments.getUserId();
                List<UserFanDTO> userFanDTO = userFollowService.getFans(userId);
                for(UserFanDTO fan : userFanDTO) {
                    String key = "subscribed-" + fan.getUserProfiles().getUserId();
                    String subscribedListStr = redisTemplate.opsForValue().get(key);
                    List<UserMoments> subscribedList;
                    subscribedList = StringUtil.isNullOrEmpty(subscribedListStr) ?
                                     new ArrayList<>() :
                                     JSONArray.parseArray(subscribedListStr, UserMoments.class);
                    subscribedList.add(userMoments);
                    redisTemplate.opsForValue().set(key, JSONObject.toJSONString(subscribedList));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        return consumer;
    }
}
