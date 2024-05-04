package com.sobczyk.walletMicroservices.position.performance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PositionPerfValue {
    private BigDecimal quantity;
    private BigDecimal marketValue;
    private BigDecimal investedValue;
    private BigDecimal TWR;
    private boolean tradingDay;

    public PositionPerfValue(BigDecimal quantity) {
        this.quantity = quantity;
        this.marketValue = BigDecimal.ZERO;
        this.investedValue = BigDecimal.ZERO;
        this.TWR = BigDecimal.ZERO;
        this.tradingDay = false;
    }

}
