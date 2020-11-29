package cn.chiu.demo.rocketmq.hellormq;

import cn.chiu.demo.rocketmq.RocketmqCommon;
import com.google.common.base.Charsets;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author by Raza
 * @classname Consumer
 * @description TODO
 * @date 2020/11/29 0029 0:21
 */
public class ConsumerTest {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer pushConsumer = RocketmqCommon.getDefaultMQPushConsumer();

        pushConsumer.registerMessageListener((MessageListenerConcurrently)(msgs, context) -> {
            System.out.println("获取消息：" + msgs);
            System.out.println(context);
            for (MessageExt msg : msgs) {
                System.out.println("接收消息：" + new String(msg.getBody(), Charsets.UTF_8));
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        pushConsumer.start();

    }
}
