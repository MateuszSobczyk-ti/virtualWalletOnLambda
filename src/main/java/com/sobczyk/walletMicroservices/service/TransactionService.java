package com.sobczyk.walletMicroservices.service;

import com.sobczyk.walletMicroservices.dto.requests.TransactionRequest;
import com.sobczyk.walletMicroservices.entity.*;
import com.sobczyk.walletMicroservices.repository.AssetRepository;
import com.sobczyk.walletMicroservices.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AssetRepository assetRepository;

    public ResponseEntity<?> saveTransaction(Investor investor, TransactionRequest request) {
        Asset asset = Asset.builder()
                .assetType(AssetType.getById(request.assetType()))
                .ticker(request.ticker())
                .build();
        assetRepository.save(asset);
        Transaction transaction = Transaction.builder()
                .investorId(investor.getId())
                .asset(asset)
                .transactionType(TransactionType.getById(request.transactionType()))
                .transaction_quantity(request.quantity())
                .purchase_price(request.price())
                .trading_fees(request.fees())
                .transaction_date(request.date())
                .description(request.description())
                .build();
        transactionRepository.save(transaction);
        return ResponseEntity.ok().build();
    }

}
