package com.lht.utils;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lht
 * @date 2021/4/1 - 21:23
 * @description: 批量删除工具
 */
public class BatchRemoveUtils {
    public static List<Long> getLongTypeListByStr(String ids) {
        String[] idStr = ids.split(",");
        List<Long> list = new ArrayList<>();
        for (String s : idStr) {
            list.add(Long.parseLong(s));
        }
        return list;
    }

    public static List<Integer> getIntegerTypeListByStr(String ids) {
        String[] idStr = ids.split(",");
        List<Integer> list = new ArrayList<>();
        for (String s : idStr) {
            list.add(Integer.parseInt(s));
        }
        return list;
    }

}
