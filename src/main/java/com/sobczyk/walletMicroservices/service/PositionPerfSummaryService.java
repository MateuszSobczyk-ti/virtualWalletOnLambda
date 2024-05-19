package com.sobczyk.walletMicroservices.service;

import com.sobczyk.walletMicroservices.position.performance.PositionPerfKey;
import com.sobczyk.walletMicroservices.position.performance.PositionPerfValue;
import com.sobczyk.walletMicroservices.position.performance.TimeSeries;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class PositionPerfSummaryService {

    public static final String TICKER_OVERALL = "OVERALL";
    public static final String TICKER_DEPOSITED = "_DEPOSITED";
    public static final String TICKER_CASH = "CASH";

    BigDecimal previousMarketValue = null;
    BigDecimal previousTWR = BigDecimal.ONE;

    public Map<PositionPerfKey, PositionPerfValue> generateOverallPosition(TimeSeries timeSeries, Map<PositionPerfKey,
            PositionPerfValue> positionsMap) {
        Map<PositionPerfKey, PositionPerfValue> currentPositionsMap = new HashMap<>();
        LocalDate date = LocalDate.now().minusMonths(timeSeries.getSerieInMonth());
        BigDecimal previousDeposited = BigDecimal.ZERO;
        while (date.isBefore(LocalDate.now())) {
            PositionPerfValue ovValue = new PositionPerfValue(BigDecimal.ZERO);
            PositionPerfValue cashValue = new PositionPerfValue(BigDecimal.ZERO);
            LocalDate finalDate = date;
            positionsMap.entrySet().stream()
                    .filter(p -> p.getKey().getDate().equals(finalDate))
                    .forEach(p -> {
                        ovValue.setTradingDay(true);
                        if (TICKER_DEPOSITED.equals(p.getKey().getTicker())) {
                            cashValue.setQuantity(p.getValue().getInvestedValue());
                        } else {
                            ovValue.setInvestedValue(ovValue.getInvestedValue().add(p.getValue().getInvestedValue()));
                            ovValue.setMarketValue(ovValue.getMarketValue().add(p.getValue().getMarketValue()));
                        }
                    });
            if (ovValue.isTradingDay()) {
                //calculate cash flow
                cashValue.setInvestedValue(cashValue.getQuantity().add(previousDeposited.negate()));
                previousDeposited = cashValue.getQuantity();
                //calculate available cash at account
                cashValue.setMarketValue(cashValue.getQuantity().add(ovValue.getInvestedValue().negate()));
                ovValue.setMarketValue(ovValue.getMarketValue().add(cashValue.getMarketValue()));
                BigDecimal twr = this.calculateTwr(ovValue, cashValue);
                ovValue.setTWR(twr.multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_DOWN));
                previousMarketValue = ovValue.getMarketValue();
                previousTWR = twr;
                positionsMap.put(new PositionPerfKey(date, TICKER_OVERALL), ovValue);
                positionsMap.put(new PositionPerfKey(date, TICKER_CASH), cashValue);
                currentPositionsMap.clear();
                currentPositionsMap.put(new PositionPerfKey(date, TICKER_OVERALL), ovValue);
                currentPositionsMap.put(new PositionPerfKey(date, TICKER_CASH), cashValue);
            }
            date = date.plusDays(1);
        }
        previousTWR = BigDecimal.ZERO;
        previousMarketValue = null;
        return currentPositionsMap;
    }

    private BigDecimal calculateTwr(PositionPerfValue ovValue, PositionPerfValue cashValue) {
        if (!ovValue.getMarketValue().add(cashValue.getInvestedValue()).equals(BigDecimal.ZERO) && previousMarketValue != null) {
            return previousTWR.multiply(ovValue.getMarketValue().divide(previousMarketValue.add(cashValue.getInvestedValue()),
                    7, RoundingMode.HALF_EVEN)).setScale(10, RoundingMode.HALF_EVEN);
        } else {
            return BigDecimal.ONE;
        }
    }
}
