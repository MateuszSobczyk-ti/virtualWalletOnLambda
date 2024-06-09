package com.sobczyk.walletMicroservices.service;

import com.sobczyk.walletMicroservices.position.performance.PositionPerfKey;
import com.sobczyk.walletMicroservices.position.performance.PositionPerfValue;
import com.sobczyk.walletMicroservices.position.performance.TimeSeries;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@Getter
public class PositionPerfSummaryService {

    public static final String TICKER_OVERALL = "OVERALL";
    public static final String TICKER_DEPOSITED = "_DEPOSITED";

    BigDecimal previousMarketValue = null;
    BigDecimal previousTWR = BigDecimal.ONE;
    PositionPerfValue ovValue;
    PositionPerfValue cashValue;
    Map<PositionPerfKey, PositionPerfValue> allTimeOverall;

    public void generateOverallPosition(TimeSeries timeSeries, Map<PositionPerfKey, PositionPerfValue> positionsMap) {
        allTimeOverall = new HashMap<>();
        Set<PositionPerfKey> positionsToRemove = new HashSet<>();
        LocalDate date = LocalDate.now().minusMonths(timeSeries.getSerieInMonth());
        BigDecimal previousDeposited = BigDecimal.ZERO;
        while (date.isBefore(LocalDate.now())) {
            PositionPerfValue ovValueTemp = new PositionPerfValue(BigDecimal.ZERO);
            PositionPerfValue cashValueTemp = new PositionPerfValue(BigDecimal.ZERO);
            LocalDate finalDate = date;
            positionsMap.entrySet().stream()
                    .filter(p -> p.getKey().getDate().equals(finalDate))
                    .forEach(p -> {
                        ovValueTemp.setTradingDay(true);
                        if (TICKER_DEPOSITED.equals(p.getKey().getTicker())) {
                            cashValueTemp.setQuantity(p.getValue().getInvestedValue());
                            positionsToRemove.add(p.getKey());
                        } else {
                            ovValueTemp.setInvestedValue(ovValueTemp.getInvestedValue().add(p.getValue().getInvestedValue()));
                            ovValueTemp.setMarketValue(ovValueTemp.getMarketValue().add(p.getValue().getMarketValue()));
                        }
                    });
            if (ovValueTemp.isTradingDay()) {
                //calculate cash flow
                cashValueTemp.setInvestedValue(cashValueTemp.getQuantity().add(previousDeposited.negate()));
                previousDeposited = cashValueTemp.getQuantity();
                //calculate available cash at account
                cashValueTemp.setMarketValue(cashValueTemp.getQuantity().add(ovValueTemp.getInvestedValue().negate()));
                ovValueTemp.setMarketValue(ovValueTemp.getMarketValue().add(cashValueTemp.getMarketValue()));
                BigDecimal twr = this.calculateTwr(ovValueTemp, cashValueTemp);
                ovValueTemp.setTWR(twr.multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_DOWN).add(BigDecimal.valueOf(100).negate()));
                previousMarketValue = ovValueTemp.getMarketValue();
                previousTWR = twr;
                allTimeOverall.put(new PositionPerfKey(date, TICKER_OVERALL), ovValueTemp);
                ovValue = ovValueTemp;
                cashValue = cashValueTemp;
            }
            date = date.plusDays(1);
        }
        positionsMap.keySet().removeAll(positionsToRemove);
        previousTWR = BigDecimal.ZERO;
        previousMarketValue = null;
    }

    private BigDecimal calculateTwr(PositionPerfValue ovValue, PositionPerfValue cashValue) {
        if (previousMarketValue != null && previousMarketValue.add(cashValue.getInvestedValue()).compareTo(BigDecimal.ZERO) != 0) {
            return previousTWR.multiply(ovValue.getMarketValue().divide(previousMarketValue.add(cashValue.getInvestedValue()),
                    7, RoundingMode.HALF_EVEN)).setScale(10, RoundingMode.HALF_EVEN);
        } else {
            return BigDecimal.ONE;
        }
    }
}
