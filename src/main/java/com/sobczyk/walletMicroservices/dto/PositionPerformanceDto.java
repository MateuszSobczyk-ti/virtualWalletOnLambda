package com.sobczyk.walletMicroservices.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PositionPerformanceDto {
    private LocalDate date;
    private String ticker;
    private BigDecimal quantity;
    private BigDecimal investedValue;
    private BigDecimal marketValue;
    private BigDecimal TWR;

    public PositionPerformanceDto() {
        clearValues();
    }

    public void clearValues() {
        quantity = BigDecimal.ZERO;
        marketValue = BigDecimal.ZERO;
        investedValue = BigDecimal.ZERO;
        TWR = BigDecimal.ZERO;
    }
}
