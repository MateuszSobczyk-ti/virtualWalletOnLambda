package com.sobczyk.walletMicroservices.service;

import com.sobczyk.walletMicroservices.handler.TooManyApiCallsException;
import com.sobczyk.walletMicroservices.position.performance.PositionPerfKey;
import io.polygon.kotlin.sdk.rest.*;
import io.polygon.kotlin.sdk.rest.stocks.PreviousCloseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FinancialDataProvider {

    private final PolygonApiClient polygonApiClient;
    private final Map<PositionPerfKey, BigDecimal> priceMap = new HashMap<>();
    private String ticker;

    public Map<PositionPerfKey, BigDecimal> retrieveStockData(String ticker, String timespan, Long multiplier, LocalDate dateFrom) {
        this.ticker = ticker;
        if (PositionPerfSummaryService.TICKER_DEPOSITED.equals(ticker)) {
            this.generateCashPrice();
        } else {
            AggregatesDTO aggs = this.polygonApiClient.getAggregatesBlocking(ticker, timespan, multiplier, dateFrom);
            PreviousCloseDTO previousCloseDTO = this.polygonApiClient.getPreviousCloseDto(ticker);
            if (("ERROR").equals(aggs.getStatus()) || ("ERROR").equals(previousCloseDTO.getStatus())) {
                throw new TooManyApiCallsException();
            }
            aggs.getResults().forEach(this::addToMap);
            previousCloseDTO.getResults().stream().findFirst().ifPresent(this::addToMap);
        }
        return priceMap;
    }

    private void addToMap(AggregateDTO agg) {
        this.priceMap.put(new PositionPerfKey(this.convertToDate(agg.getTimestampMillis()), this.ticker),
                BigDecimal.valueOf(agg.getClose()));
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
