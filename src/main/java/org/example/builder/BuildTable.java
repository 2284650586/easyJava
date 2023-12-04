package org.example.builder;

import org.apache.commons.lang3.ArrayUtils;
import org.example.bean.Constants;
import org.example.bean.FieldInfo;
import org.example.bean.TableInfo;
import org.example.utils.PropertiesUtil;
import org.example.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuildTable {

    private static Connection conn = null;
    private static final Logger logger = LoggerFactory.getLogger(BuildTable.class);
    private static final String SQL_SHOW_TABLE_STATUS = "show table status";
    private static final String SQL_SHOW_TABLE_FIELDS = "show full fields from %s";

    static {
        String driverName = PropertiesUtil.getString("db.driver.name");
        String url = PropertiesUtil.getString("db.url");
        String user = PropertiesUtil.getString("db.username");
        String password = PropertiesUtil.getString("db.password");

        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            logger.error("数据库连接失败", e);
        }
    }

    public static void getTables() {
        PreparedStatement ps = null;
        ResultSet tableResult = null;

        List<TableInfo> tableInfoList = new ArrayList<>();

        try {
            ps = conn.prepareStatement(SQL_SHOW_TABLE_STATUS);
            tableResult = ps.executeQuery();
            while (tableResult.next()) {
                String tableName = tableResult.getString("name");
                String comment = tableResult.getString("comment");

                TableInfo tableInfo = new TableInfo();


                tableInfo.setTableName(tableName);

                String beanName = tableName;
                if (Constants.IGNORE_TABLE_PREFIX) {
                    beanName = tableName.substring(beanName.indexOf("_") + 1);
                }

                beanName = processField(beanName, false);

                tableInfo.setBeanName(beanName);
                tableInfo.setComment(comment);
                tableInfo.setBeanParamName(beanName + Constants.SUFFIX_BEAN_PARAM);
                logger.info("表:{}, 备注:{}, javaBean:{}, javaParaBean:{}",
                        tableInfo.getTableName(), tableInfo.getComment(), tableInfo.getBeanName(), tableInfo.getBeanParamName());

                readFieldInfo(tableInfo);
                tableInfoList.add(tableInfo);


            }
        } catch (Exception e) {
            logger.error("读取表失败");
        } finally {
            if (tableResult != null) {
                try {
                    tableResult.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static List<FieldInfo> readFieldInfo(TableInfo tableInfo) {
        PreparedStatement ps = null;
        ResultSet fieldResult = null;

        List<FieldInfo> fieldInfoList = new ArrayList<>();

        try {
            ps = conn.prepareStatement(String.format(SQL_SHOW_TABLE_FIELDS, tableInfo.getTableName()));
            fieldResult = ps.executeQuery();

            boolean haveDate = false;
            boolean haveBigDecimal = false;

            while (fieldResult.next()) {
                String field = fieldResult.getString("field");
                String type = fieldResult.getString("type");
                String extra = fieldResult.getString("extra");
                String comment = fieldResult.getString("comment");
                if (type.indexOf("(") > 0) {
                    type = type.substring(0, type.indexOf("("));
                }

                String propertyName = processField(field, false);

                FieldInfo fieldInfo = new FieldInfo();

                fieldInfo.setFieldName(field);
                fieldInfo.setComment(comment);
                fieldInfo.setSqlType(type);
                fieldInfo.setAutoIncrement("auto_increment".equalsIgnoreCase(extra));
                fieldInfo.setPropertyName(propertyName);
                fieldInfo.setJavaType(processJavaType(type));


                if(ArrayUtils.contains(Constants.SQL_DATE_TYPES,type) || ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES,type)){
                    haveDate = true;
                }
                if(ArrayUtils.contains(Constants.SQL_DECIMAL_TYPE,type)){
                    haveBigDecimal = true;
                }

                tableInfo.setFieldInfoList(fieldInfoList);


                tableInfo.setHaveBigDecimal(haveBigDecimal);
                tableInfo.setHaveDate(haveDate);
                tableInfo.setHaveDateTime(haveDate);

                fieldInfoList.add(fieldInfo);


                logger.info("field:{}, type:{}, extre:{}, comment:{}, propertyName:{}, javaType:{}",
                        field, type, extra, comment, propertyName, processJavaType(type));
            }
        } catch (Exception e) {
            logger.error("读取表失败");
        } finally {
            if (fieldResult != null) {
                try {
                    fieldResult.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return fieldInfoList;
    }

    private static String processField(String field, Boolean upCaseFirstLetter) {
        StringBuffer sb = new StringBuffer();
        String[] fields = field.split("_");
        if (upCaseFirstLetter) {
            StringUtil.uperCaseFirstLetter(fields[0]);
        }
        sb.append(fields[0]);
        for (int i = 1, len = fields.length; i < fields.length; i++) {
            sb.append(StringUtil.uperCaseFirstLetter(fields[i]));
        }
        return sb.toString();
    }

    private static String processJavaType(String type) {
        if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, type) || ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, type)) {
            return "Date";
        } else if (ArrayUtils.contains(Constants.SQL_DECIMAL_TYPE, type)) {
            return "BigDecimal";
        } else if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, type)) {
            return "String";
        } else if (ArrayUtils.contains(Constants.SQL_INTEGER_TYPE, type)) {
            return "Integer";
        } else if (ArrayUtils.contains(Constants.SQL_LONG_TYPE, type)) {
            return "Long";
        } else {
            throw new RuntimeException("无法识别的类型:" + type);
        }
    }

}
