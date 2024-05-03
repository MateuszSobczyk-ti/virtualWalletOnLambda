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
    private BigDecimal marketValue;
    private BigDecimal cashFlow;
    private BigDecimal TWR;

    public PostionPerformanceResponse() {
        quantity = BigDecimal.ZERO;
        marketValue = BigDecimal.ZERO;
        cashFlow = BigDecimal.ZERO;
        TWR = BigDecimal.ZERO;
    }
}
