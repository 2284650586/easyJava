package org.example;

import org.example.bean.TableInfo;
import org.example.builder.BuildTable;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        List<TableInfo> tableInfoList = BuildTable.getTables();
    }
}
