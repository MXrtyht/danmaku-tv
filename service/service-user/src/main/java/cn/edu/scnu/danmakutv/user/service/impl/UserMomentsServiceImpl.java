package cn.edu.scnu.danmakutv.user.service.impl;

import cn.edu.scnu.common.utils.RocketMQUtil;
import cn.edu.scnu.danmakutv.domain.user.UserMoments;
import cn.edu.scnu.danmakutv.domain.user.UserProfiles;
import cn.edu.scnu.danmakutv.user.constant.UserMomentsConstant;
import cn.edu.scnu.danmakutv.user.mapper.UserMomentsMapper;
import cn.edu.scnu.danmakutv.user.service.UserMomentsService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.rocketmq.common.message.Message;
import org.springframework.context.ApplicationContext;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class UserMomentsServiceImpl extends ServiceImpl<UserMomentsMapper, UserMoments> implements UserMomentsService {
    @Autowired()
    private ApplicationContext applicationContext;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void addUserMoments(UserMoments userMoments) throws Exception {
        userMoments.setCreateTime(new Date());
        userMoments.setId(null); // 让数据库id自增auto-increment
        baseMapper.insert(userMoments);
        DefaultMQProducer producer = applicationContext.getBean("momentsProducer", DefaultMQProducer.class);
        Message msg = new Message(UserMomentsConstant.TOPIC_MOMENTS, JSONObject.toJSONString(userMoments).getBytes(StandardCharsets.UTF_8));
        RocketMQUtil.syncSendMsg(producer, msg);
    }

    @Override
    public List<UserMoments> getUserSubscribedMoments(Long userId) throws Exception {
        String key = "subscribed-" + userId;
        String listStr = redisTemplate.opsForValue().get(key);
        if(listStr==null) {
        	return this.baseMapper.selectList(
                    new QueryWrapper<>(UserMoments.class)
                    .in("user_id", userId));
        }
        return JSONArray.parseArray(listStr, UserMoments.class);
    }
}
