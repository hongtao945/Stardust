package com.lht.constant;

/**
 * @Author lht
 * @date 2021/10/30 - 18:56
 * @description: redis中的常量
 */
public interface RedisConstant {
    /**
     * 前台的页面访问量key
     */
    String VIEW_KEY = "FRONT_VIEWS";

    /**
     * 前台的用户访问量数， 一个ip认为是一个用户
     */
    String IP_KEY = "FRONT_PERSON";

    /**
     * 今日访问量前缀
     */
    String TODAY_VIEWS_PRE = "TODAY_VIEWS_";

    /**
     * 半个月
     */
    int HALF_MONTH = 16;
}
