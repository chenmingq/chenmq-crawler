package com.chenmq.crawler.mq.spidermq;

import com.alibaba.fastjson.JSONObject;
import com.chenmq.crawler.crawler.domain.SpiderRule;
import com.chenmq.crawler.crawler.processing.WebSpiderFactory;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;

/**
 * @author chenmq
 * @version V1.0
 * @ProjectName: chenmq-crawler
 * @Package com.chenmq.crawler.mq.spidermq
 * @Description: 获取待消费的任务
 * @date 2018-04-05 下午9:31
 */

@Component
@Service
public class SpiderConsumerMQ {

    private Logger logger = LoggerFactory.getLogger(SpiderConsumerMQ.class);

    private final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();
    private final ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());


    @Resource
    private WebSpiderFactory webSpiderFactory;


    /**
     * 消费者的组名
     */
    @Value("${apache.rocketmq.consumer.chenmq.spider.consumer}")
    private String consumerGroup;

    /**
     * NameServer 地址
     */
    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    private final String TOPIC = "CHENMQ_NOW_SPIDER_QUEUE";

    private final String TAG = "CHENMQ_SPIDER_TAG";

    @PostConstruct
    public void defaultMQPushConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        try {
            consumer.subscribe(TOPIC, TAG);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener((MessageListenerConcurrently) (list, context) -> {
                try {
                    for (MessageExt messageExt : list) {
                        singleThreadPool.execute(new Runnable() {
                            @Override
                            public void run() {
                                String messageBody = null;
                                try {
                                    messageBody = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                                if (StringUtils.isNotBlank(messageBody)) {
                                    SpiderRule spiderRule = JSONObject.parseObject(messageBody,SpiderRule.class);
                                    webSpiderFactory.initPageType(spiderRule);
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("消息订阅出现异常 === >>> {}",e.getMessage());
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                logger.info("{}","消费成功");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
        } catch (Exception e) {
            logger.error("消息出现异常 === >>> {}",e.getMessage());
            e.printStackTrace();
        }
    }

}
