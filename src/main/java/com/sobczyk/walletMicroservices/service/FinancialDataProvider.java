package com.sobczyk.walletMicroservices.service;

import io.polygon.kotlin.sdk.rest.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Service
public class FinancialDataProvider {

    @Value("${polygon.key}")
    private String POLYGON_KEY;

    private final Map<PositionPerfKey, BigDecimal> priceMap = new HashMap<>();
    private String ticker;

    public Map<PositionPerfKey, BigDecimal> retrieveStockData(String ticker, String timespan, Long multiplier, LocalDate dateFrom) {
        this.ticker = ticker;
        PolygonRestClient client = new PolygonRestClient(POLYGON_KEY);
        AggregatesDTO aggs = client.getAggregatesBlocking(
                new AggregatesParametersBuilder()
                        .ticker(ticker)
                        .timespan(timespan)
                        .multiplier(multiplier)
                        .fromDate(dateFrom.toString())
                        .toDate(LocalDate.now().toString())
                        .build()
        );
        if (PositionPerfSummaryService.TICKER_DEPOSITED.equals(ticker)) {
            this.generateCashPrice();
        } else {
            aggs.getResults().forEach(this::convertToMap);
        }
        return priceMap;
    }

    private void convertToMap(AggregateDTO aggDto) {
        this.priceMap.put(new PositionPerfKey(this.convertToDate(aggDto.component8()), this.ticker),
                BigDecimal.valueOf(aggDto.component5()));
    }

    private LocalDate convertToDate(Long mSec) {
        return Instant.ofEpochMilli(mSec).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void generateCashPrice() {
        //first priceMap must be loaded
        Map<PositionPerfKey, BigDecimal> cashPriceMap = new HashMap<>();
        this.priceMap.forEach((key, value) -> cashPriceMap.putIfAbsent(
            new PositionPerfKey(key.getDate(), PositionPerfSummaryService.TICKER_DEPOSITED),
            BigDecimal.ONE));
        this.priceMap.putAll(cashPriceMap);
    }
}
