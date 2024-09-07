package com.sobczyk.walletMicroservices.position.performance;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class PositionPerfKey {

    private LocalDate date;
    private String ticker;

}
