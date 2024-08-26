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
     * Object가 null인지 확인
     * @param str
     * @return
     */
    public static boolean isNull(Object obj) {
        if(obj == null) {
            return true;
        } else {
            return false;
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

    /**
     * 문자열을 날짜로 변환
     * yyy-MM-dd 형식
     * @param str
     * @return String
     */
    public static String dataformat(String str) {

        if (str.length() == 4) 
            str = str.substring(0, 4);
        else if (str.length() == 6) 
            str = str.substring(0, 4) + "-" + str.substring(4, 6);
        else if (str.length() == 8) 
            str = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
        
        return str;
    }

    /*
     * 오늘 날짜 구하기
     * yyyy-MM-dd 형식
     */
    public static String today() {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        return format.format(date);
    }

    /*
     * 시작일 부터 종료일 까지 몇년 몇개월인지 계산
     * yyyy-MM-dd 형식
     * @param start
     * @param end
     */
    public static String getPeriod(String start, String end) {
        String period = "";
        int sYear = Integer.parseInt(start.substring(0, 4));
        int sMonth = Integer.parseInt(start.substring(5, 7));
        int eYear = Integer.parseInt(end.substring(0, 4));
        int eMonth = Integer.parseInt(end.substring(5, 7));
        int year = eYear - sYear;
        int month = eMonth - sMonth;
        if (month < 0) {
            year--;
            month = 12 + month;
        }
        period = year + "년 " + month + "개월";
        return period;
    }
}
