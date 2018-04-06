package com.chenmq.crawler.core.spring;

/**
 * @author chenmq
 * @version V1.0
 * @ProjectName: chenmq-crawler
 * @Package com.chenmq.crawler.core.spring
 * @Description: 异常处理
 * @date 2018-04-05 下午11:03
 */

public class ChenmqCrawlerException extends Exception {

    public ChenmqCrawlerException() {
    }

    public ChenmqCrawlerException(String message) {
        super(message);
    }

    public ChenmqCrawlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChenmqCrawlerException(Throwable cause) {
        super(cause);
    }

    public ChenmqCrawlerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
