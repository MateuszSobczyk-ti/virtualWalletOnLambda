package com.sobczyk.walletMicroservices.service;

import com.sobczyk.walletMicroservices.dto.requests.PositionPerformanceRequest;
import com.sobczyk.walletMicroservices.dto.responses.PostionPerformanceResponse;
import com.sobczyk.walletMicroservices.entity.Investor;
import com.sobczyk.walletMicroservices.entity.Transaction;
import com.sobczyk.walletMicroservices.entity.TransactionType;
import com.sobczyk.walletMicroservices.repository.TransactionRepository;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PositionPerformanceService {

    private final TransactionRepository transactionRepository;
    private final FinancialDataProvider financialDataProvider;
    private final PositionPerfSummaryService positionPerfSummaryService;

    private final Map<PositionPerfKey, PositionPerfValue> positionsMap = new HashMap<>();
    private PostionPerformanceResponse previousPerf = new PostionPerformanceResponse();
    private Map<PositionPerfKey, BigDecimal> priceMap = new HashMap<>();

    public ResponseEntity<List<PostionPerformanceResponse>> getPositionPerformance(Investor investor, PositionPerformanceRequest request) {
        clearVariables();
        TimeSeries timeSeries = TimeSeries.getById(request.timeSeries());
        transactionRepository.findTransactions(investor.getId())
                .forEach(t -> {
                    populatePositions(t.getAsset().getTicker(), previousPerf.getQuantity(), previousPerf.getPurchasingPrice(),
                            previousPerf.getTradingFees(), timeSeries, t.getTransaction_date());
                    onTickerChangedAction(timeSeries, t.getAsset().getTicker());
                    this.setPreviousPerf(t);
                });
        //populate from now to last transaction
        populatePositions(previousPerf.getTicker(), previousPerf.getQuantity(), previousPerf.getPurchasingPrice(),
                previousPerf.getTradingFees(), timeSeries, LocalDate.now().plusDays(1));
        this.positionsMap.putAll(positionPerfSummaryService.generateOverallPosition(timeSeries, this.positionsMap));
        List<PostionPerformanceResponse> list = new ArrayList<>(this.positionsMap.entrySet().stream()
                .map(this::convertPositionMapToResponse)
                .toList());
        //comparing to delete
        list.sort(Comparator
                .comparing(PostionPerformanceResponse::getTicker)
                .thenComparing(PostionPerformanceResponse::getDate));
        return ResponseEntity.ok().body(list);
    }

    private void setPreviousPerf(Transaction t) {
        previousPerf.setQuantity(afterTransactionQuantityCalc(previousPerf.getQuantity(), t.getTransaction_quantity(), t.getTransactionType()));
        previousPerf.setPurchasingPrice(t.getPurchase_price());
        previousPerf.setTradingFees(t.getTrading_fees());
        previousPerf.setTicker(t.getAsset().getTicker());
    }

    private BigDecimal afterTransactionQuantityCalc(BigDecimal currentAmount, BigDecimal changedAmount, TransactionType transactionType) {
        if (TransactionType.BUY.equals(transactionType) || TransactionType.DEPOSIT.equals(transactionType)) {
            return currentAmount.add(changedAmount);
        } else {
            return currentAmount.add(changedAmount.negate());
        }
    }

    private void populatePositions(String ticker, BigDecimal afterTransQuantity, BigDecimal purchasingPrice,
                                   BigDecimal fee, TimeSeries timeSeries, LocalDate date) {
        while (LocalDate.now().minusMonths(timeSeries.getSerieInMonth()).isBefore(date)) {
            PositionPerfKey key = new PositionPerfKey(date, ticker);
            if (this.positionsMap.containsKey(key)) {
                break;
            }
            PositionPerfValue posValue = new PositionPerfValue(afterTransQuantity);
            posValue.setInvestedValue(posValue.getInvestedValue().add(afterTransQuantity.multiply(purchasingPrice)).add(fee));
            try {
                posValue.setMarketValue(afterTransQuantity.multiply(priceMap.get(key)));
            } catch (NullPointerException e) {
                date = date.minusDays(1);
                continue;
            }
            this.positionsMap.put(key, posValue);
            date = date.minusDays(1);
        }
    }

    private void onTickerChangedAction(TimeSeries timeSeries, String ticker) {
        if (!ticker.equals(previousPerf.getTicker())) {
            if (previousPerf.getTicker() != null) {
                populatePositions(previousPerf.getTicker(), previousPerf.getQuantity(), previousPerf.getPurchasingPrice(),
                        previousPerf.getTradingFees(), timeSeries, LocalDate.now().plusDays(1));
            }
            priceMap = financialDataProvider.retrieveStockData(ticker, timeSeries.getTimeSpan(), timeSeries.getMultiplier(),
                    LocalDate.now().minusMonths(timeSeries.getSerieInMonth()));
            previousPerf.setQuantity(BigDecimal.ZERO);
        }
    }

    private PostionPerformanceResponse convertPositionMapToResponse(Map.Entry<PositionPerfKey, PositionPerfValue> entry) {
        return PostionPerformanceResponse.builder()
                .date(entry.getKey().getDate().minusDays(1))
                .ticker(entry.getKey().getTicker())
                .quantity(entry.getValue().getQuantity())
                .marketValue(entry.getValue().getMarketValue())
                .investedValue(entry.getValue().getInvestedValue())
                .build();
    }

    private void clearVariables() {
        this.positionsMap.clear();
        this.previousPerf = new PostionPerformanceResponse();
    }
}

