package com.sobczyk.walletMicroservices.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ReportCycle {
    WEEK(1), MONTH(2);

    private final int id;

    public static ReportCycle getById(int id) {
        return Arrays.stream(ReportCycle.values())
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(ReportCycle.WEEK);
    }
}
