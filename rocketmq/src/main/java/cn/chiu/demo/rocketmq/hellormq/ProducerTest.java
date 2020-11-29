package cn.chiu.demo.rocketmq.hellormq;

import cn.chiu.demo.rocketmq.RocketmqCommon;
import com.google.common.base.Charsets;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author by yeachiu
 * @classname Producer
 * @description TODO
 * @date 2020/11/23 0023 22:05
 */
public class ProducerTest {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = RocketmqCommon.getDefaultMQProducer();

        // 启动
        producer.start();

        String msg = "hello rocketmq -- " + new Date();
        Message message = new Message("HELLO_WORLD", "test", msg.getBytes(Charsets.UTF_8));
        SendResult sendResult = producer.send(message);
        System.out.println("消息发送状态：" + sendResult.getSendStatus());
        System.out.println("消息ID：" + sendResult.getMsgId());
        System.out.println("消息队列：" + sendResult.getMessageQueue());

        // 关闭
        producer.shutdown();
    }

}
