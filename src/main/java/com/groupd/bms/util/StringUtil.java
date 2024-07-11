package com.groupd.bms.util;


/**
 * StringUtil
 * 문자열 처리를 위한 유틸리티 클래스
 * @version 1.0
 * @since 2024.04.26
 * @see com.groupd.bms.util.StringUtil
 */
public class StringUtil {

    /**
     * 문자열이 null 또는 빈 문자열인지 확인
     * @param str
     * @return boolean
     */
    public static boolean isNull(String str) {
        if(str == null || str.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 문자열을 정수로 변환
     * @param str
     * @return Integer
     */
    public static Integer stringToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 문자열을 실수로 변환
     * @param str
     * @return Double
     */
    public static Double stringToDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 객체를 문자열로 변환
     * @param obj
     * @return String
     */
    public static String objectToString(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return obj.toString();
        }
    }
    
    /**
     * 객체를 문자열로 변환
     * @param obj
     * @param init
     * @return String
     */
    public static String objectToString(Object obj, String init) {
        if (obj == null) {
            return init;
        } else {
            return obj.toString();
        }
    }
}
