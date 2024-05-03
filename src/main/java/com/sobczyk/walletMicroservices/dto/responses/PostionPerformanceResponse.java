package com.sobczyk.walletMicroservices.dto.responses;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PostionPerformanceResponse {
    private LocalDate date;
    private String ticker;
    private BigDecimal quantity;
    private BigDecimal purchasingPrice;
    private BigDecimal investedValue;
    private BigDecimal tradingFees;
    private BigDecimal marketValue;
    private BigDecimal TWR;

    public PostionPerformanceResponse() {
        quantity = BigDecimal.ZERO;
        marketValue = BigDecimal.ZERO;
        investedValue = BigDecimal.ZERO;
        purchasingPrice = BigDecimal.ZERO;
        tradingFees = BigDecimal.ZERO;
        TWR = BigDecimal.ZERO;
    }
}
