package com.lht.utils;

import com.qiniu.storage.Region;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author lht
 * @date 2021/4/1 - 19:38
 * @description: 七牛云的工具类
 */
public class QiniuUtils {

    private static final String HUADONG = "华东";
    private static final String HUABEI = "华北";
    private static final String HUANAN = "华南";
    private static final String BEIMEI = "北美";

    /**
     * 获取地区信息
     * @param zone
     * @return
     */
    public static Region getRegionByZone(String zone) {
        if (HUANAN.equals(zone)) {
            return Region.huanan();
        }else if (HUADONG.equals(zone)) {
            return Region.huadong();
        }else if (HUABEI.equals(zone)) {
            return Region.huabei();
        }else if (BEIMEI.equals(zone)) {
            return Region.beimei();
        }else {
            return Region.qvmHuadong();
        }
    }

    /**
     * 以时间为标志创建新的文件名
     * @param name
     * @return
     */
    public static String createFileName(String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return FileUtil.getFileNameNoEx(name)+"-"+sdf.format(date)+"."+FileUtil.getExtensionName(name);
    }
}
