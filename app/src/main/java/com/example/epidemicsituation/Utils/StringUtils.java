package com.example.epidemicsituation.Utils;

import java.util.List;

/**
 * @description: 字符串相关工具类
 * @author: ODM
 * @date: 2019/11/4
 */
public class StringUtils {


    public static String ConcatToStringByLongList(List<Long> mList) {

        int length = mList.size();
        if(length <= 0 ) {
            return "";
        } else {
            StringBuilder targetString = new StringBuilder();
            targetString.append(mList.get(0));
            for(int i = 1 ; i < length; i++) {
                targetString.append(",");
                targetString.append(mList.get(i));
            }
            return targetString.toString();
        }
    }
}
