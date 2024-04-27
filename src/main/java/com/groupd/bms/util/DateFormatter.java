package com.groupd.bms.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * DateFormatter
 * 날짜 포맷을 지정하기 위한 클래스
 * @version 1.0
 * @since 2024.04.26
 * @see com.groupd.bms.controller.login.DateFormatter
 */
public class DateFormatter {

    private static final DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd (E) ");
    private static final DateTimeFormatter shortFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
    private static final DateTimeFormatter customFormatteryyyymmdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    
    /**
     * Returns current date in format yyyy.MM.dd (E)
     * @return
     */
    public static String formatCurrentDate() {
        return formatCurrentDate(defaultFormatter);
    }

    /**
     * Returns current date in format dd-MM-yyyy
     * @return
     */
    public static String formatShortDate() {
        return formatCurrentDate(shortFormatter);
    }

    /**
     * Returns current date in format MMMM dd, yyyy
     * @return
     */
    public static String formatCustomDate() {
        return formatCurrentDate(customFormatter);
    }

    /**
     * Returns current date in format yyyyMMdd
     * @return
     */
    public static String formatCustomDateyyyymmdd() {
        return formatCurrentDate(customFormatteryyyymmdd);
    }

    /**
     * Returns current date in format yyyyMMdd
     * @return
     */
    public static String bizCallFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = formatter.format(currentDateTime);
        return formattedDateTime;
    }

    public static String formatCurrentDate(DateTimeFormatter formatter) {
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);

        String dayOfWeek = currentDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault());
        return formattedDate.replace("Today", dayOfWeek);
    }
}