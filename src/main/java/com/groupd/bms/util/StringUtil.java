package com.groupd.bms.util;

/**
 * StringUtil
 * 문자열 처리를 위한 유틸리티 클래스
 * @version 1.0
 * @since 2024.04.26
 * @see com.groupd.bms.util.StringUtil
 */
public class StringUtil {

    public static boolean isNull(String str) {
        if(str == null || str.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static Integer stringToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double stringToDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public static String objectToString(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return obj.toString();
        }
    }
}
