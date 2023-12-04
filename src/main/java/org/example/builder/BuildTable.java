package org.example.builder;

import org.example.bean.Constants;
import org.example.bean.TableInfo;
import org.example.utils.PropertiesUtil;
import org.example.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuildTable {

    private static Connection conn = null;
    private static final Logger logger = LoggerFactory.getLogger(BuildTable.class);
    private static final String SQL_SHOW_TABLE_STATUS = "show table status";

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


    public static void main(String[] args) {
        System.out.printf(processField("info_code", true));
    }

}
