package com.chenmq.crawler.crawler.webpage;

import com.chenmq.crawler.cache.utils.CacheKeys;
import com.chenmq.crawler.core.utils.CommonType;
import com.chenmq.crawler.crawler.datapipeline.DataPipeline;
import com.chenmq.crawler.crawler.domain.SpiderRule;
import com.chenmq.crawler.crawler.domain.po.JianShuPO;
import com.chenmq.crawler.crawler.processing.WebPageProcessing;
import com.chenmq.crawler.webmagic.selenium.SeleniumDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenmq
 * @version V1.0
 * @ProjectName: chenmq-crawler
 * @Package com.chenmq.crawler.crawler.webpage
 * @Description: 简书页面
 * @date 2018-04-05 下午11:42
 */

@Service
public class JianshuPage implements WebPageProcessing {

    private Logger logger = LoggerFactory.getLogger(JianshuPage.class);

    @Resource
    private DataPipeline dataPipeline;

    private Site site = Site.me().
            setRetryTimes(3).
            setUserAgent(CommonType.USER_AGENT).
            setSleepTime(100);

    /**
     * 根域名
     */
    private final String DO_MAIN = "https://www.jianshu.com";

    /**
     * 文章页面请求
     */
    private final String DO_MAIN_COMMENTS = "/p";

    /**
     * 首页列表所有数据
     */
    private final String PAGE_LIST_ALL = "//*[@id=\"list-container\"]/ul/li";
    /**
     * 某个列表元素
     */
    private final String PAGE_UL_LI = PAGE_LIST_ALL+"[%d]";
    /**
     * 某个列表元素首页图片
     */
    private final String INDEX_IMAGE = PAGE_UL_LI+"/a/img/@src";
    /**
     * 某个列表元素作者头像
     */
    private final String HEADER_IMAGE = PAGE_UL_LI+"/div/div/a/img/@src";
    /**
     * 某个列表元素文章标题
     */
    private final String TITLE = PAGE_UL_LI+"/div/a/text()";
    /**
     * 某个列表元素作者
     */
    private final String AUTHOR = PAGE_UL_LI+"/div/div/div/a/text()";
    /**
     * 某个列表元素文章最后修改时间
     */
    private final String UPDATE_TIME =  PAGE_UL_LI+"/div/div/div/span/@data-shared-at";
    /**
     * 某个列表元素文章链接
     */
    private final String ARTICLE_LINK = PAGE_UL_LI+"/div/a/@href";
    /**
     * 某个列表元素文章喜欢数量
     */
    private final String LIKE_NUM = PAGE_UL_LI+"/div/div[2]/span/text()";
    /**
     * 某个列表元素文章阅读数量
     */
    private final String READ_NUM = PAGE_UL_LI+"/div/div[2]/a[1]/text()";
    /**
     * 某个列表元素文章字数
     */
    private final String COMMENTS_NUM = PAGE_UL_LI+"/div/div[2]/a[2]/text()";
    /**
     * 某个列表元素文章正文
     */
    private final String BODY = "/html/body/div[1]/div[1]/div[1]/div[2]";

    @Override
    public void process(Page page) {

        logger.info("page.getUrl() == > {}",page.getUrl());
        logger.info("page.getHtml() {}",page.getHtml());

        String startUrl = DO_MAIN+DO_MAIN_COMMENTS;

        if (page.getUrl().toString().startsWith(startUrl)) {
            String body = page.getHtml().xpath(BODY).get();
            page.putField(CacheKeys.JIAN_SHU_KEY+"_#p#_"+page.getUrl(),body);
        } else {
            List<String> list = page.getHtml().xpath(PAGE_LIST_ALL).all();

            List<JianShuPO> jianShuPOList = new ArrayList<>();
            for (int i = 1; i <= list.size(); i++) {

                JianShuPO jianShuPO = new JianShuPO();

                String indexImage = page.getHtml().xpath(String.format(INDEX_IMAGE,i)).get();
                String headerImage = page.getHtml().xpath(String.format(HEADER_IMAGE,i)).get();
                String title =  page.getHtml().xpath(String.format(TITLE,i)).get();
                String author = page.getHtml().xpath(String.format(AUTHOR,i)).get();
                String updateTime = page.getHtml().xpath(String.format(UPDATE_TIME,i)).get();
                String articleLink = page.getHtml().xpath(String.format(ARTICLE_LINK,i)).get();
                String likeNum = page.getHtml().xpath(String.format(LIKE_NUM,i)).get();
                String readNum = page.getHtml().xpath(String.format(READ_NUM,i)).get();
                String commentsNum = page.getHtml().xpath(String.format(COMMENTS_NUM,i)).get();
                Integer currentPageSorting = i;

                jianShuPO.setArticleLink(articleLink);
                jianShuPO.setUpdateTime(updateTime);
                jianShuPO.setTitle(title);
                jianShuPO.setReadNumber(readNum);
                jianShuPO.setLikeNum(likeNum);
                jianShuPO.setIndexImage(indexImage);
                jianShuPO.setHeaderImage(headerImage);
                jianShuPO.setCurrentPageSorting(currentPageSorting);
                jianShuPO.setAuthor(author);
                jianShuPO.setCommentsNumber(commentsNum);

                jianShuPOList.add(jianShuPO);
                page.addTargetRequest(DO_MAIN+articleLink);

            }
            page.putField(CacheKeys.JIAN_SHU_KEY,jianShuPOList);
        }


    }

    @Override
    public Site getSite() {
        return this.site;
    }

    @Override
    public void start(SpiderRule spiderRule) {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader
                .setProxyProvider(
                        SimpleProxyProvider
                                .from(
                                        new Proxy("36.26.104.180",38273)));
        Spider
                .create(this)
                .setDownloader(new SeleniumDownloader())
                .addPipeline(dataPipeline)
                .setDownloader(httpClientDownloader)
                .addUrl(DO_MAIN)
                .thread(3)
                .run();
    }
}
