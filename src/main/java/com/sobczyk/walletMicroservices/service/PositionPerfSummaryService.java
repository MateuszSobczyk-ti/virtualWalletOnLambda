package com.sobczyk.walletMicroservices.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class PositionPerfSummaryService {

    public static final String TICKER_OVERALL = "OVERALL";
    public static final String TICKER_DEPOSITED = "DEPOSITED";
    public static final String TICKER_CASH = "CASH";

    public Map<PositionPerfKey, PositionPerfValue> generateOverallPosition(TimeSeries timeSeries, Map<PositionPerfKey, PositionPerfValue> positionsMap) {
        Map<PositionPerfKey, PositionPerfValue> summaryPositionsMap = new HashMap<>();
        LocalDate date = LocalDate.now();
        while (LocalDate.now().minusMonths(timeSeries.getSerieInMonth()).isBefore(date)) {
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
            cashValue.setInvestedValue(cashValue.getQuantity().add(ovValue.getInvestedValue().negate()));
            cashValue.setMarketValue(cashValue.getQuantity().add(ovValue.getInvestedValue().negate()));
            ovValue.setMarketValue(ovValue.getMarketValue().add(cashValue.getMarketValue()));
            if (ovValue.isTradingDay()) {
                summaryPositionsMap.put(new PositionPerfKey(date, TICKER_OVERALL), ovValue);
                summaryPositionsMap.put(new PositionPerfKey(date, TICKER_CASH), cashValue);
                ovValue.setTradingDay(false);
            }
            date = date.minusDays(1);
        }
        return summaryPositionsMap;
    }
}
