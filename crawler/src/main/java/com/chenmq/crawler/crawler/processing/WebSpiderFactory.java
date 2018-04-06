package com.chenmq.crawler.crawler.processing;

import com.chenmq.crawler.crawler.domain.SpiderRule;

/**
 * @author chenmq
 * @version V1.0
 * @ProjectName: chenmq-crawler
 * @Package com.chenmq.crawler.crawler.processing
 * @Description: TODO
 * @date 2018-04-05 下午11:29
 */

public interface WebSpiderFactory<T> {

    /**
     * 平台类型
     * @param t
     */
    void initPageType (SpiderRule t);
}
