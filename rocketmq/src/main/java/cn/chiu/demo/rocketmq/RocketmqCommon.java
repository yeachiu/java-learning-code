package cn.chiu.demo.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * @author by yeachiu
 * @classname RocketmqCommon
 * @description Rocketmq Common
 * @date 2020/11/29 0029 16:55
 */
public class RocketmqCommon {
    public final static String NAME_SRV_ADDR = "91.216.169.95:9876";
    public final static String DEFAULT_GROUP = "hello-group";
    public final static String DEFAULT_TOPIC = "HELLO_WORLD";

    /**
     * 获取生产者对象
     *
     * @return Producer with default config
     */
    public static DefaultMQProducer getDefaultMQProducer() {
        DefaultMQProducer producer = new DefaultMQProducer(DEFAULT_GROUP);
        producer.setNamesrvAddr(NAME_SRV_ADDR);
        return producer;
    }

    /**
     * 获取消费者对象
     *
     * @return Consumer with default config
     * @throws MQClientException
     */
    public static DefaultMQPushConsumer getDefaultMQPushConsumer() throws MQClientException {
        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer(DEFAULT_GROUP);
        pushConsumer.setNamesrvAddr(NAME_SRV_ADDR);
        // 订阅topic和tag, tag="*"表示接收所有消息
        pushConsumer.subscribe(DEFAULT_TOPIC, "*");
        return pushConsumer;
    }
}
