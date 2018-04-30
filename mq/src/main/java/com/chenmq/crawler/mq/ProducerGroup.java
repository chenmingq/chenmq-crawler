package com.chenmq.crawler.mq;

import com.alibaba.fastjson.JSON;
import com.chenmq.crawler.crawler.domain.SpiderRule;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import java.util.Date;

/**
 * @author chenmq
 * @version V1.0
 * @ProjectName: chenmq-crawler
 * @Package com.chenmq.crawler.mq
 * @Description: 生产者
 * @date 2018-04-02 下午11:28
 */

public class ProducerGroup {


    public static void main(String[] args) {
        try {
            sendRocketMqTest();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生产者测试代码
     * @throws MQClientException
     */
    private static void sendRocketMqTest () throws MQClientException {
        String producerGroup = "CHENMQ_SPIDER_PRODUCE_GROUP";
       /* String topIc = "TopicTest";
        String tag = "push";*/

        String TOPIC = "CHENMQ_NOW_SPIDER_QUEUE";

        String TAG = "CHENMQ_SPIDER_TAG";

        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr("localhost:9876");
        producer.setInstanceName("producer");
        producer.start();

        for (int i = 1; i < 11; i++) {
            SpiderRule spiderRule = new SpiderRule();
            spiderRule.setCreateDate(new Date());
            spiderRule.setCreateUser(1);
            spiderRule.setSearchKeyword("iphone");
            spiderRule.setPlatFormId(i);
            spiderRule.setPlatFormName("简书");
            spiderRule.setIsDefaultList(true);

            String body = JSON.toJSONString(spiderRule);

            Message msg = new Message(TOPIC,
                    TAG,
                    (body.getBytes())
            );
            SendResult sendResult = null;
            try {
                sendResult = producer.send(msg);
            } catch (MQClientException | MQBrokerException | InterruptedException | RemotingException e) {
                e.printStackTrace();
            }
            System.out.println(sendResult);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        producer.shutdown();
    }

}
