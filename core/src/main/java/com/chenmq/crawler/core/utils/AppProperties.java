package com.chenmq.crawler.core.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author chenmq
 * @version V1.0
 * @ProjectName: chenmq-crawler
 * @Package com.chenmq.crawler.core.utils
 * @Description: TODO
 * @date 2018-04-05 下午5:53
 */

@Component
@Data
public class AppProperties {

    private static AppProperties appProperties;

    public static AppProperties getAppProperties() {
        return appProperties;
    }

    public static void setAppProperties(AppProperties appProperties) {
        AppProperties.appProperties = appProperties;
    }

    public AppProperties() {
        appProperties = this;
    }

    @Value("${chenmq.crawler.phantomjs.path}")
    private String phantomjsPath;

    @Value("${chenmq.crawler.webmagic.config-ini.path}")
    private String webmagicConfigIniPath;

}
