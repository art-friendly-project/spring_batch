package org.com.artfriendlybatch.global.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDateFormatter {
    public static String convertToString(LocalDate localDate, String format){
        DateTimeFormatter formatter;
        try{
            formatter = DateTimeFormatter.ofPattern(format);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return localDate.format(formatter);
    }
    public static LocalDate converToLocalDate(String dateStr, String format) {
        DateTimeFormatter formatter;
        try{
            formatter = DateTimeFormatter.ofPattern(format);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return LocalDate.parse(dateStr, formatter);
    }
}
