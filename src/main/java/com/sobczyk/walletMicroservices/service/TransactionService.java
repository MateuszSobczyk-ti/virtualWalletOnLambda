package com.sobczyk.walletMicroservices.service;

import com.sobczyk.walletMicroservices.dto.requests.TransactionRequest;
import com.sobczyk.walletMicroservices.entity.*;
import com.sobczyk.walletMicroservices.repository.AssetRepository;
import com.sobczyk.walletMicroservices.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AssetRepository assetRepository;

    public ResponseEntity<?> saveTransaction(Investor investor, TransactionRequest request) {
        TransactionType transactionType = TransactionType.getById(request.transactionType());
        Asset asset = Asset.builder()
                .assetType(AssetType.getById(request.assetType()))
                .ticker(transactionType.equals(TransactionType.DEPOSIT) ? PositionPerfSummaryService.TICKER_DEPOSITED : request.ticker())
                .build();
        assetRepository.saveAndFlush(asset);
        Transaction transaction = Transaction.builder()
                .investorId(investor.getId())
                .asset(asset)
                .transactionType(transactionType)
                .transaction_quantity(request.quantity())
                .purchase_price(transactionType.equals(TransactionType.DEPOSIT) ? BigDecimal.ONE : request.price())
                .trading_fees(request.fees())
                .transaction_date(LocalDate.parse(request.date(), DateTimeFormatter.ISO_LOCAL_DATE))
                .description(request.description())
                .build();
        transactionRepository.save(transaction);
        return ResponseEntity.ok().build();
    }

}
