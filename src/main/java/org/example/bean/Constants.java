package org.example.bean;

import org.example.utils.PropertiesUtil;

public class Constants {
    public static Boolean IGNORE_TABLE_PREFIX;
    public static String SUFFIX_BEAN_PARAM;

    static {
        IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtil.getString("ignore.table.prefix"));
        SUFFIX_BEAN_PARAM = PropertiesUtil.getString("suffix.bean.param");
    }

}
