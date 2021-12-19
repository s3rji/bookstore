package com.example.MyBookShopApp.util;

import java.time.LocalDate;

public final class DateUtil {
    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    private DateUtil() {
    }

    public static LocalDate atFromDateOrMin(LocalDate from) {
        return from != null ? from : MIN_DATE;
    }

    public static LocalDate atToDateOrMax(LocalDate to) {
        return to != null ? to : MAX_DATE;
    }
}
