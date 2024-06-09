package com.sobczyk.walletMicroservices.dto.requests;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionRequest(String ticker,
                                 @NotNull Integer assetType,
                                 @NotNull Integer transactionType,
                                 @NotNull BigDecimal quantity,
                                 BigDecimal price,
                                 BigDecimal fees,
                                 @NotNull String date,
                                 String description) {
}
