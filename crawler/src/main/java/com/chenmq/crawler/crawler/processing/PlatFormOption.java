package com.chenmq.crawler.crawler.processing;

import com.chenmq.crawler.core.utils.PlatformNumUtils;
import com.chenmq.crawler.crawler.domain.SpiderRule;
import com.chenmq.crawler.crawler.webpage.JianshuPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chenmq
 * @version V1.0
 * @ProjectName: chenmq-crawler
 * @Package com.chenmq.crawler.crawler.processing
 * @Description: TODO
 * @date 2018-04-05 下午10:51
 */

@Service
public class PlatFormOption implements WebSpiderFactory {

    private Logger logger = LoggerFactory.getLogger(PlatFormOption.class);

    @Resource
    private JianshuPage jianshuPage;

    @Override
    public void initPageType(SpiderRule spiderRule) {
        logger.info("spiderRule === >>> {} ", spiderRule);
        Integer platFormId = spiderRule.getPlatFormId();
        if (PlatformNumUtils.JIAN_SHU.equals(platFormId)) {
            jianshuPage.start(spiderRule);
        } else if (PlatformNumUtils.DOU_BAN.equals(platFormId)) {
            // 还没写
        } else if (PlatformNumUtils.ZHI_HU.equals(platFormId)) {

        } else if (PlatformNumUtils.TAO_BAO.equals(platFormId)) {

        } else if (PlatformNumUtils.JING_DONG.equals(platFormId)) {

        }
        // ......

    }




}
