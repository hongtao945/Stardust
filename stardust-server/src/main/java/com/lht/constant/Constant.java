package com.lht.constant;

/**
 * @Author lht
 * @date 2021/3/27 - 22:57
 * @description: 系统常量
 */
public interface Constant {

    /**
     * 菜单icon前缀
     */
    String PRE_MENU_ICON = "layui-icon ";

    /**
     * Http & Https
     */
    String HTTP = "http://";
    String HTTPS = "https://";


    /**
     * 颜色选择
     */
    String DEFAULT_COLOR = "#e77c8e";

    /**
     * 文章的审核状态
     */
    Integer AUDIT_WAIT = 1;
    Integer AUDIT_NO_PASS = 0;
    Integer AUDIT_PASS = 2;

    /**
     * 用于IP定位转换
     */
    String REGION = "内网IP|内网IP";


}
