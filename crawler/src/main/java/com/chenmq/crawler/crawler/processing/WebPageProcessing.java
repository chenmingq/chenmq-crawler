package com.chenmq.crawler.crawler.processing;

import com.chenmq.crawler.crawler.domain.SpiderRule;
import us.codecraft.webmagic.processor.PageProcessor;


/**
 * @author chenmq
 * @version V1.0
 * @ProjectName: chenmq-crawler
 * @Package com.chenmq.crawler.crawler.processing
 * @Description: TODO
 * @date 2018-04-05 下午11:39
 */

public interface WebPageProcessing extends PageProcessor {

    /**
     * 启动方法
     * @param spiderRule
     */
    void start (SpiderRule spiderRule);


}
