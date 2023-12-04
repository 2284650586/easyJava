package org.example.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
    public static String uperCaseFirstLetter(String field) {
        if (StringUtils.isEmpty(field)) {
            return field;
        }
        return field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    public static String lowerCaseFirstLetter(String field) {
        if (StringUtils.isEmpty(field)) {
            return field;
        }
        return field.substring(0, 1).toLowerCase() + field.substring(1);
    }

    public static void main(String[] args) {
        System.out.println(uperCaseFirstLetter("company"));
    }
}