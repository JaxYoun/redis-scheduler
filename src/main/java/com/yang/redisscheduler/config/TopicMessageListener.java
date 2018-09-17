package com.yang.redisscheduler.config;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author: Yang
 * @date: 2018/9/17 22:52
 * @description:
 */
@Component
public class TopicMessageListener implements MessageListener {

    /**
     * 客户端监听订阅的topic，当有消息的时候，会触发该方法
     *
     * @param message
     * @param bytes
     */
    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body = message.getBody();  // 请使用valueSerializer
        byte[] channel = message.getChannel();
        String topic = new String(channel);
        String key = new String(body);
        String eventType = new String(bytes);

        // 请参考配置文件，本例中key，value的序列化方式均为string。
        System.out.println("topic:" + topic);
        System.out.println("key:" + key);
        System.out.println("eventType:" + eventType);

    }
}
