package com.sobczyk.walletMicroservices.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(@NotEmpty String ticker,
                                 @NotNull Integer assetType,
                                 @NotNull Integer transactionType,
                                 @NotNull BigDecimal quantity,
                                 @NotNull BigDecimal price,
                                 BigDecimal fees,
                                 @NotNull LocalDate date,
                                 String description) {
}
