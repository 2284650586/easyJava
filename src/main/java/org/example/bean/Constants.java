package org.example.bean;

import org.example.utils.PropertiesUtil;

public class Constants {
    public static Boolean IGNORE_TABLE_PREFIX;
    public static String SUFFIX_BEAN_PARAM;

    static {
        IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtil.getString("ignore.table.prefix"));
        SUFFIX_BEAN_PARAM = PropertiesUtil.getString("suffix.bean.param");
    }

    //    SQL类型到JAVA类型映射
    public final static String[] SQL_DATE_TIME_TYPES = new String[]{"datetime", "timestamp"};
    public final static String[] SQL_DATE_TYPES = new String[]{"date"};

    public final static String[] SQL_DECIMAL_TYPE = new String[]{"decimal", "double", "float"};
    public final static String[] SQL_STRING_TYPE = new String[]{"char", "varchar", "text", "mediumtext", "longtext"};
    public final static String[] SQL_INTEGER_TYPE = new String[]{"int", "tinyint"};
    public final static String[] SQL_LONG_TYPE = new String[]{"bigint"};

}
