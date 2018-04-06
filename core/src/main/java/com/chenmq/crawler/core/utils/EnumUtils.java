package com.chenmq.crawler.core.utils;

/**
 * @author chenmq
 * @version V1.0
 * @ProjectName: chenmq-crawler
 * @Package com.chenmq.crawler.core.utils
 * @Description: TODO
 * @date 2018-04-05 下午9:11
 */

public class EnumUtils {

    /**
     * 平台对应
     */
    public enum PLATFORM_LIST{
        JIAN_SHU(1,"简书"),
        DOU_BAN(2,"豆瓣"),
        ZHI_HU(3,"知呼"),
        TAO_BAO(4,"淘宝"),
        JING_DONG(5,"京东");

        public Integer KEY;
        public String VALUE;
        PLATFORM_LIST(Integer KEY,String VALUE) {
            this.KEY = KEY;
            this.VALUE = VALUE;
        }

        @Override
        public String toString() {
            return this.KEY + "_" + this.VALUE;
        }
    }


}
