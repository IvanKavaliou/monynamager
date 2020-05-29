package net.ivan.kavaliou.moneyman.utils;

import java.time.LocalDateTime;

public class DateTimeUtils {
    public static final LocalDateTime MIN_DATE = LocalDateTime.of(2000, 01, 01,00,00);
    public static final LocalDateTime MAX_DATE = LocalDateTime.of(3000, 01, 01,00,00);

    public static LocalDateTime getStartOfMount(){
        LocalDateTime now = LocalDateTime.now();
        return  LocalDateTime.of(now.getYear(), now.getMonth(), 01, 00,00);
    }

    public static LocalDateTime getEndOfMount(){
        LocalDateTime now = LocalDateTime.now();
        return  LocalDateTime.of(now.getYear(), now.getMonth(), now.getMonth().length(now.toLocalDate().isLeapYear()), 00,00);
    }

}
