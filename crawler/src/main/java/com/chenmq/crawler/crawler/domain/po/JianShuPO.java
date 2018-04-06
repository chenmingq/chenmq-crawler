package com.chenmq.crawler.crawler.domain.po;

import lombok.Data;

/**
 * @author chenmq
 * @version V1.0
 * @ProjectName: chenmq-crawler
 * @Package com.chenmq.crawler.crawler.domain.po
 * @Description: TODO
 * @date 2018-04-06 上午1:38
 */

@Data
public class JianShuPO {

    /**
     * 标题
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 最后编辑时间
     */
    private String updateTime;

    /**
     * 内容
     */
    private String body;

    /**
     * 首页图片
     */
    private String indexImage;

    /**
     * 喜欢数量
     */
    private String likeNum;

    /**
     * 文章链接
     */
    private String articleLink;

    /**
     * 文章字数
     */
    private String articleNumber;

    /**
     * 阅读数量
     */
    private String readNumber;

    /**
     * 评论数量
     */
    private String commentsNumber;

    /**
     * 当前页面排序
     */
    private Integer currentPageSorting;

    /**
     * 头像
     */
    private String headerImage;
}
