package com.chenmq.crawler.crawler.datapipeline;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chenmq.crawler.cache.sources.RedisSourcesCache;
import com.chenmq.crawler.crawler.domain.po.JianShuPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author chenmq
 * @version V1.0
 * @ProjectName: chenmq-crawler
 * @Package com.chenmq.crawler.crawler.datapipeline
 * @Description: TODO
 * @date 2018-04-05 下午4:23
 */

@Service
public class DataPipeline implements ResultItemsPipeline {

    private Logger logger = LoggerFactory.getLogger(DataPipeline.class);


    @Resource
    private RedisSourcesCache redisSourcesCache;



    @Override
    public void process(ResultItems resultItems, Task task) {

        /* System.out.println("get page: " + resultItems.getRequest().getUrl());*/

        /*遍历所有结果，输出到控制台，上面例子中的"author"、"name"、"readme"都是一个key，其结果则是对应的value*/
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            logger.info("ResultItemsPipeline  === >>> {},{}",entry.getKey() , entry.getValue());
            if (null != entry.getValue()) {
                List<JianShuPO> jianShuPOList = JSONArray.parseArray( entry.getValue().toString(),JianShuPO.class);
                for (JianShuPO j:jianShuPOList) {
                    redisSourcesCache.hset(entry.getKey(),j.getArticleLink(), JSON.toJSONString(j));
                }
            }
        }

    }
}
