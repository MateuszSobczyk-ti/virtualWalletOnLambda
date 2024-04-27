package com.sobczyk.walletMicroservices.service;

import com.sobczyk.walletMicroservices.dto.requests.TransactionRequest;
import com.sobczyk.walletMicroservices.entity.*;
import com.sobczyk.walletMicroservices.repository.AssetRepository;
import com.sobczyk.walletMicroservices.repository.PositionRepository;
import com.sobczyk.walletMicroservices.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final PositionRepository positionRepository;
    private final AssetRepository assetRepository;

    public ResponseEntity<?> saveTransaction(TransactionRequest request) {
        Optional<Position> optPosition = positionRepository.getPostionByTicker(request.ticker());
        if (optPosition.isPresent()) {
            Position currentPosition = optPosition.get();
            BigDecimal newAmount = afterTransactionAmount(currentPosition.getAmount(), request.quantity(), TransactionType.getById(request.transactionType()));
            saveTransaction(request, currentPosition.getAsset(), newAmount);
            currentPosition.setAmount(newAmount);
            currentPosition.setClosed_at(newAmount.equals(BigDecimal.ZERO) ? request.date() : null);
            positionRepository.save(currentPosition);
        } else {
            Asset asset = Asset.builder()
                    .assetType(AssetType.getById(request.assetType()))
                    .ticker(request.ticker())
                    .build();
            assetRepository.save(asset);
            Position position = Position.builder()
                    .amount(request.quantity())
                    .asset(asset)
                    .opened_at(request.date())
                    .build();
            positionRepository.save(position);
            saveTransaction(request, asset, request.quantity());
        }
        return ResponseEntity.ok().build();
    }

    private void saveTransaction(TransactionRequest request, Asset asset, BigDecimal newAmount) {
        Transaction transaction = Transaction.builder()
                .asset(asset)
                .transactionType(TransactionType.getById(request.transactionType()))
                .transaction_quantity(request.quantity())
                .after_transaction_quantity(newAmount)
                .purchase_price(request.price())
                .total_amount(request.quantity().multiply(request.price()))
                .trading_fees(request.fees())
                .transaction_date(request.date())
                .description(request.description())
                .build();
        transactionRepository.save(transaction);
    }

    private BigDecimal afterTransactionAmount(BigDecimal currentAmount, BigDecimal changedAmount, TransactionType transactionType) {
        if (TransactionType.BUY.equals(transactionType) || TransactionType.DEPOSIT.equals(transactionType)) {
            return currentAmount.add(changedAmount);
        } else {
            return currentAmount.add(changedAmount.negate());
        }
    }
}
