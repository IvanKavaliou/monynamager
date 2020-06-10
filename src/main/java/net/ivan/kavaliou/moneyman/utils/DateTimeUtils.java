package net.ivan.kavaliou.moneyman.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static final LocalDateTime MIN_DATE = LocalDateTime.of(2000, 01, 01,00,00);
    public static final LocalDateTime MAX_DATE = LocalDateTime.of(3000, 01, 01,00,00);
    public static final String LDT_INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm";

    public static LocalDateTime getStartOfMount(){
        LocalDateTime now = LocalDateTime.now();
        return  LocalDateTime.of(now.getYear(), now.getMonth(), 01, 00,00);
    }

    public static LocalDateTime getEndOfMount(){
        LocalDateTime now = LocalDateTime.now();
        return  LocalDateTime.of(now.getYear(), now.getMonth(), now.getMonth().length(now.toLocalDate().isLeapYear()), 00,00);
    }

    public static LocalDateTime parseDate(String date, String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(date, formatter);
    }

}
