package com.sobczyk.walletMicroservices.position.performance;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TimeSeries {
    MONTH(1, 1, "day", 1L),
    SIX_MONTHS(2, 6, "week", 1L),
    YEAR(3, 12, "week", 2L),
    TWO_YEARS(4, 24, "month", 1L);

    private final int id;
    private final int serieInMonth;
    private final String timeSpan;
    private final Long multiplier;

    public static TimeSeries getById(int id) {
        return Arrays.stream(TimeSeries.values())
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(TimeSeries.MONTH);
    }

}
