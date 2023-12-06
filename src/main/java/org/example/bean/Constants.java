package org.example.bean;

import org.example.utils.PropertiesUtil;

import java.nio.file.Path;

public class Constants {
    //    固定字符串
    public static String PRIVATE = "private";
    public static String PUBLIC = "public";

    public static String STATIC = "static";
    public static String PATH_JAVA = "java";
    public static String PATH_RESOURCE = "resources";
    //    作者
    public static String AUTHOR_COMMENT;
    //    忽略表前缀
    public static boolean IGNORE_TABLE_PREFIX;
    //    参数bean后缀
    public static String SUFFIX_BEAN_QUERY;
    //  参数模糊搜索后缀
    public static String SUFFIX_BEAN_QUERY_FUZZY;
    //  参数日期起至
    public static String SUFFIX_BEAN_QUERY_TIME_START;
    public static String SUFFIX_BEAN_QUERY_TIME_END;
    //    Mapper后缀
    public static String SUFFIX_MAPPERS;
    //    文件输出路径
    public static String PATH_BASE;
    //    包
    public static String PACKAGE_BASE;
    //    bean
    public static String PATH_PO;
    public static String PACKAGE_PO;
    //    vo
    public static String PATH_VO;
    public static String PACKAGE_VO;
    //    query包
    public static String PATH_QUERY;
    public static String PACKAGE_QUERY;
    //    util包
    public static String PACKAGE_UTILS;
    public static String PATH_UTILS;
    //    枚举包
    public static String PATH_ENUMS;
    public static String PACKAGE_ENUMS;
    //    Mapper包
    public static String PATH_MAPPERS;
    public static String PACKAGE_MAPPERS;
    //    Service包
    public static String PATH_SERVICE;
    public static String PACKAGE_SERVICE;
    //    ServiceImpl包
    public static String PATH_SERVICEIMPL;
    public static String PACKAGE_SERVICEIMPL;
    //    controller包
    public static String PATH_CONTROLLER;
    public static String PACKAGE_CONTROLLER;
    //    exception包
    public static String PATH_EXCEPTION;
    public static String PACKAGE_EXCEPTION;
    //    XML路径
    public static String PATH_MAPPERS_MXLS;
    //    需要忽略的属性
    public static String IGNORE_BEAN_TOJSON_FIELD;
    public static String IGNORE_BEAN_TOJSON_EXPRESSION;
    public static String IGNORE_BEAN_TOJSON_CLASS;
    //    日期格式序列化
    public static String BEAN_DATE_FORMAT_EXPRESS;
    public static String BEAN_DATE_FORMAT_CLASS;
    //    日期格式反序列化
    public static String BEAN_DATE_UNFORMAT_EXPRESS;
    public static String BEAN_DATE_UNFORMAT_CLASS;

    //    SQL类型到JAVA类型映射
    public final static String[] SQL_DATE_TIME_TYPES = new String[]{"datetime", "timestamp"};
    public final static String[] SQL_DATE_TYPES = new String[]{"date"};

    public final static String[] SQL_DECIMAL_TYPE = new String[]{"decimal","double","float"};
    public final static String[] SQL_STRING_TYPE = new String[]{"char","varchar","text","mediumtext","longtext"};
    public final static String[] SQL_INTEGER_TYPE = new String[]{"int","tinyint"};
    public final static String[] SQL_LONG_TYPE = new String[]{"bigint"};


    static {
        IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtil.getString("ignore.table.prefix"));
        SUFFIX_BEAN_QUERY = PropertiesUtil.getString("suffix.bean.param");

        PACKAGE_BASE = PropertiesUtil.getString("package.base");
        PACKAGE_PO = PACKAGE_BASE + "." + PropertiesUtil.getString("package.po");
        PACKAGE_VO = PACKAGE_BASE + "." + PropertiesUtil.getString("package.vo");
        PACKAGE_QUERY = PACKAGE_BASE + "." + PropertiesUtil.getString("package.query");

        PATH_BASE = PropertiesUtil.getString("path.base") + PATH_JAVA + "/" + PACKAGE_BASE.replace('.','/');
        PATH_PO = PATH_BASE + "/" + PropertiesUtil.getString("package.po").replace('.','/') + "/";
        PATH_VO = PATH_BASE + "/" + PropertiesUtil.getString("package.vo").replace('.','/') + "/";
        PATH_QUERY = PATH_BASE + "/" + PropertiesUtil.getString("package.query").replace('.','/') + "/";


    }

    public static void main(String[] args) {
        System.out.println(PATH_PO);
        System.out.println(PATH_BASE);
    }

}
