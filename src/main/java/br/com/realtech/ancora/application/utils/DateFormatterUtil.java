package br.com.realtech.ancora.application.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatterUtil {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static LocalDate transformStringToLocalDate(String date) {
        return LocalDate.parse(date, dateFormatter);
    }
}
