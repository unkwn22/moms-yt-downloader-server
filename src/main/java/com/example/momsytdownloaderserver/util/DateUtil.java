package com.example.momsytdownloaderserver.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public static LocalDate toLocalDate(String stringDate) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return LocalDate.parse(stringDate, format);
    }
}
