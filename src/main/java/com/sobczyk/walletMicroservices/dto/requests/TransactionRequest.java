package com.sobczyk.walletMicroservices.dto.requests;

import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequest(@NotEmpty String ticker,
                                 @NotEmpty Integer assetType,
                                 @NotEmpty Integer transactionType,
                                 @NotEmpty BigDecimal quantity,
                                 @NotEmpty BigDecimal price,
                                 @NotEmpty BigDecimal fees,
                                 @NotEmpty LocalDateTime date,
                                 @NotEmpty String description) {
}
