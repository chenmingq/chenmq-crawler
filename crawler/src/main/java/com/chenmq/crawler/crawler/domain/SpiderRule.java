package com.chenmq.crawler.crawler.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author chenmq
 * @version V1.0
 * @ProjectName: chenmq-crawler
 * @Package com.chenmq.crawler.crawler.domain
 * @Description: 采集规则
 * @date 2018-04-05 下午5:46
 */

@Data
public class SpiderRule {

    /**
     * 搜索关键词
     */
    private String searchKeyword;

    /**
     * 平台名称
     */
    private String platFormName;

    /**
     * 平台id
     */
    private Integer platFormId;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 默认列表数据
     */
    private Boolean isDefaultList;

    /**
     * 创建时间爱你
     */
    private Date createDate;

}
